package db

import (
	"context"
	"database/sql"
	"fmt"
	"testing"

	"github.com/stretchr/testify/require"
	"github.com/thobbiz/Stockey/utilities"
)

func TestOrderTx(t *testing.T) {
	user := createRandomUser(t)
	store := NewStore(testDB)
	customer := createRandomCustomer(t)

	product1, err := store.CreateProduct(
		context.Background(),
		CreateProductParams{
			Owner:        user.Username,
			Name:         utilities.RandomName(),
			CostPrice:    int64(1000),
			SellingPrice: int64(1500),
			Quantity:     50,
			Unit:         utilities.RandomString(6),
		},
	)
	require.NoError(t, err)
	require.NotEmpty(t, product1)
	require.NotZero(t, product1.ID)
	require.NotZero(t, product1.CreatedAt)

	product2, err := store.CreateProduct(
		context.Background(),
		CreateProductParams{
			Owner:        user.Username,
			Name:         utilities.RandomName(),
			CostPrice:    int64(1000),
			SellingPrice: int64(1500),
			Quantity:     50,
			Unit:         utilities.RandomString(6),
		},
	)
	require.NoError(t, err)
	require.NotEmpty(t, product2)

	require.NotZero(t, product2.ID)
	require.NotZero(t, product2.CreatedAt)

	orderInput := []OrderInput{
		{
			ProductID: product1.ID,
			Quantity:  2,
		},

		{
			ProductID: product2.ID,
			Quantity:  3,
		},
	}

	arg := OrderTxParams{
		CustomerID:    sql.NullInt64{Int64: customer.ID, Valid: true},
		PaymentMethod: PaymentMethodBankTransfer,
		OrderInput:    orderInput,
		Comment:       sql.NullString{String: "test Order", Valid: true},
	}

	result, err := store.OrderTx(context.Background(), arg)
	require.NoError(t, err)
	require.NotEmpty(t, result)

	require.NotZero(t, result.Order.ID)
	require.Equal(t, customer.ID, result.Order.CustomerID.Int64)

	require.Len(t, result.OrderProducts, 2)
	require.Equal(t, result.Order.ID, result.OrderProducts[0].OrderID)
	require.Equal(t, result.Order.ID, result.OrderProducts[1].OrderID)

	require.Len(t, result.Entry, 2)
	require.Equal(t, int64(-2), result.Entry[0].Quantity)
	require.Equal(t, int64(-3), result.Entry[1].Quantity)

	updatedProduct1, err := store.GetProduct(context.Background(), product1.ID)
	require.NoError(t, err)
	require.Equal(t, updatedProduct1.Quantity, product1.Quantity-2)

	updatedProduct2, err := store.GetProduct(context.Background(), product2.ID)
	require.NoError(t, err)
	require.Equal(t, updatedProduct2.Quantity, product2.Quantity-3)
}

func TestOrderTxConcurrent(t *testing.T) {
	store := NewStore(testDB)
	user := createRandomUser(t)
	amount := int64(2)

	customer := createRandomCustomer(t)
	product, err := store.CreateProduct(context.Background(), CreateProductParams{
		Owner:        user.Username,
		Name:         "Limited Product",
		CostPrice:    950,
		SellingPrice: 1000,
		Quantity:     10,
		Unit:         "bags",
		Description:  sql.NullString{String: "why", Valid: true},
	})
	require.NoError(t, err)

	orderInput := []OrderInput{
		{
			ProductID: product.ID,
			Quantity:  int64(amount),
		},
	}

	n := 5
	errs := make(chan error)
	results := make(chan OrderTxResult)

	for i := 0; i < n; i++ {
		go func() {
			arg := OrderTxParams{
				CustomerID:    sql.NullInt64{Int64: customer.ID, Valid: true},
				PaymentMethod: PaymentMethodBankTransfer,
				OrderInput:    orderInput,
				Comment:       sql.NullString{String: "test Order", Valid: true},
			}

			result, err := store.OrderTx(context.Background(), arg)
			errs <- err
			results <- result
		}()
	}

	for i := 0; i < n; i++ {
		err := <-errs
		require.NoError(t, err)

		result := <-results
		require.NotEmpty(t, result)

		order := result.Order
		require.NotEmpty(t, order)
		require.Equal(t, order.CustomerID.Int64, customer.ID)
		require.NotZero(t, order.CreatedAt)
		require.NotZero(t, order.ID)

		_, err = store.GetOrder(context.Background(), order.ID)
		require.NoError(t, err)

		entries := result.Entry
		for _, entry := range entries {
			require.NotEmpty(t, entry)
			require.NotZero(t, entry.CreatedAt)
			require.NotZero(t, entry.ID)
			require.Equal(t, entry.ProductID, product.ID)
			require.Equal(t, entry.Quantity, -amount)
		}

		orderProducts := result.OrderProducts
		for _, orderProduct := range orderProducts {
			require.NotEmpty(t, orderProduct)
			require.Equal(t, orderProduct.Price, product.SellingPrice)
			require.NotZero(t, orderProduct.OrderID, order.ID)
			require.Equal(t, orderProduct.ProductID, product.ID)
		}
	}

	// Verify final product quantity
	finalProduct, err := store.GetProduct(context.Background(), product.ID)
	require.NoError(t, err)
	require.Equal(t, int64(0), finalProduct.Quantity)
}

func TestOrderTxDeadlock(t *testing.T) {
	store := NewStore(testDB)
	user := createRandomUser(t)
	amount := int64(2)
	customer := createRandomCustomer(t)
	productA, _ := store.CreateProduct(context.Background(), CreateProductParams{
		Owner:        user.Username,
		Name:         "Product A",
		CostPrice:    950,
		SellingPrice: 1000,
		Quantity:     100,
		Unit:         "bags",
		Description:  sql.NullString{String: "why", Valid: true},
	})
	productB, _ := store.CreateProduct(context.Background(), CreateProductParams{
		Owner:        user.Username,
		Name:         "Product B",
		CostPrice:    950,
		SellingPrice: 1000,
		Quantity:     100,
		Unit:         "bags",
		Description:  sql.NullString{String: "why", Valid: true},
	})

	orderInput1 := []OrderInput{
		{ProductID: productA.ID, Quantity: amount},
		{ProductID: productB.ID, Quantity: amount},
	}
	orderInput2 := []OrderInput{
		{ProductID: productB.ID, Quantity: amount},
		{ProductID: productA.ID, Quantity: amount},
	}

	n := 15
	errs := make(chan error, n)

	for i := 0; i < n; i++ {
		i := i // Capture loop variable
		txName := fmt.Sprintf("tx %d", i+1)

		if i%2 == 0 {
			go func() {
				ctx := context.WithValue(context.Background(), txKey, txName)
				arg := OrderTxParams{
					CustomerID:    sql.NullInt64{Int64: customer.ID, Valid: true},
					PaymentMethod: PaymentMethodBankTransfer,
					OrderInput:    orderInput1,
					Comment:       sql.NullString{String: "test Order", Valid: true},
				}
				_, err := store.OrderTx(ctx, arg)
				errs <- err
			}()
		} else {
			go func() {
				ctx := context.WithValue(context.Background(), txKey, txName)
				arg := OrderTxParams{
					CustomerID:    sql.NullInt64{Int64: customer.ID, Valid: true},
					PaymentMethod: PaymentMethodBankTransfer,
					OrderInput:    orderInput2,
					Comment:       sql.NullString{String: "test Order", Valid: true},
				}
				_, err := store.OrderTx(ctx, arg)
				errs <- err
			}()
		}
	}

	for i := 0; i < n; i++ {
		err := <-errs
		require.NoError(t, err)
	}
}

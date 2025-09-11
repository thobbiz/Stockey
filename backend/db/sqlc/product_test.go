package db

import (
	"context"
	"database/sql"
	"testing"
	"time"

	"github.com/stretchr/testify/require"
	"github.com/thobbiz/Stockey/utilities"
)

func createRandomProduct(t *testing.T) Product {
	arg := CreateProductParams{
		Name:         utilities.RandomName(),
		CostPrice:    utilities.RandomPrice(),
		SellingPrice: utilities.RandomPrice(),
		Quantity:     utilities.RandomQuantity(),
		Unit:         utilities.RandomUnit(),
	}

	product, err := testQueries.CreateProduct(context.Background(), arg)
	require.NoError(t, err)
	require.NotEmpty(t, product)

	require.Equal(t, arg.Name, product.Name)
	require.Equal(t, arg.CostPrice, product.CostPrice)
	require.Equal(t, arg.SellingPrice, product.SellingPrice)

	require.NotZero(t, product.ID)
	require.NotZero(t, product.CreatedAt)

	return product
}

func TestCreateProduct(t *testing.T) {
	createRandomProduct(t)
}

func TestGetProduct(t *testing.T) {
	product1 := createRandomProduct(t)
	product2, err := testQueries.GetProduct(context.Background(), product1.ID)

	require.NoError(t, err)
	require.NotEmpty(t, product2)

	require.Equal(t, product1.ID, product2.ID)
	require.Equal(t, product1.Name, product2.Name)
	require.Equal(t, product1.CostPrice, product2.CostPrice)
	require.Equal(t, product1.SellingPrice, product2.SellingPrice)
	require.WithinDuration(t, product1.CreatedAt, product2.CreatedAt, time.Second)
}

func TestUpdateProduct(t *testing.T) {
	product1 := createRandomProduct(t)
	x := product1.Quantity
	arg := AddQuantityParams{
		ID:     product1.ID,
		Amount: utilities.RandomQuantity(),
	}

	product2, err := testQueries.AddQuantity(context.Background(), arg)
	require.NoError(t, err)
	require.NotEmpty(t, product2)

	require.Equal(t, product1.ID, product2.ID)
	require.Equal(t, product1.Name, product2.Name)
	require.Equal(t, product1.CostPrice, product2.CostPrice)
	require.Equal(t, arg.Amount, product2.Quantity-x)
	require.Equal(t, product1.SellingPrice, product2.SellingPrice)
	require.WithinDuration(t, product1.CreatedAt, product2.CreatedAt, time.Second)
}

func TestDeleteProduct(t *testing.T) {
	product1 := createRandomProduct(t)
	err := testQueries.DeleteProduct(context.Background(), product1.ID)
	require.NoError(t, err)

	account2, err := testQueries.GetProduct(context.Background(), product1.ID)
	require.Error(t, err)
	require.EqualError(t, err, sql.ErrNoRows.Error())
	require.Empty(t, account2)
}

func TestListProduct(t *testing.T) {
	for i := 0; i < 10; i++ {
		createRandomProduct(t)
	}

	arg := ListProductsParams{
		Limit:  5,
		Offset: 5,
	}

	accounts, err := testQueries.ListProducts(context.Background(), arg)
	require.NoError(t, err)
	require.Len(t, accounts, 5)

	for _, account := range accounts {
		require.NotEmpty(t, account)
	}
}

package db

import (
	"context"
	"database/sql"
	"testing"
	"time"

	"github.com/stretchr/testify/require"
)

func createRandomOrder(t *testing.T) Order {
	customer := createRandomCustomer(t)
	arg := CreateOrderParams{
		CustomerID: sql.NullInt64{
			Int64: customer.ID,
			Valid: true,
		},
		TotalAmount: 2700,
		Comment: sql.NullString{
			String: "",
			Valid:  true,
		},
	}

	order, err := testQueries.CreateOrder(context.Background(), arg)
	order.OrderStatus = OrderStatusCompleted
	order.PaymentMethod = PaymentMethodBankTransfer
	require.NoError(t, err)
	require.NotEmpty(t, order)

	require.Equal(t, arg.CustomerID, order.CustomerID)
	require.Equal(t, arg.TotalAmount, order.TotalAmount)
	require.NotEqual(t, order.OrderStatus, OrderStatusPending)
	require.NotEqual(t, order.PaymentMethod, PaymentMethodNotSelected)

	require.NotZero(t, order.ID)
	require.NotZero(t, order.CreatedAt)

	return order
}

func TestCreateOrder(t *testing.T) {
	createRandomOrder(t)
}

func TestGetOrder(t *testing.T) {
	order1 := createRandomOrder(t)
	order2, err := testQueries.GetOrder(context.Background(), order1.ID)

	require.NoError(t, err)
	require.NotEmpty(t, order2)

	require.Equal(t, order1.ID, order2.ID)
	require.Equal(t, order1.CustomerID, order2.CustomerID)
	require.Equal(t, order1.TotalAmount, order2.TotalAmount)
	require.Equal(t, order1.Comment, order2.Comment)
	require.WithinDuration(t, order1.CreatedAt, order2.CreatedAt, time.Second)
}

func TestDeleteOrder(t *testing.T) {
	order1 := createRandomOrder(t)
	err := testQueries.DeleteOrder(context.Background(), order1.ID)
	require.NoError(t, err)

	order2, err := testQueries.GetOrder(context.Background(), order1.ID)
	require.Error(t, err)
	require.EqualError(t, err, sql.ErrNoRows.Error())
	require.Empty(t, order2)
}

func TestListOrders(t *testing.T) {
	for i := 0; i < 10; i++ {
		createRandomOrder(t)
	}

	arg := ListOrdersParams{
		Limit:  5,
		Offset: 5,
	}

	orders, err := testQueries.ListOrders(context.Background(), arg)
	require.NoError(t, err)
	require.Len(t, orders, 5)

	for _, order := range orders {
		require.NotEmpty(t, order)
	}
}

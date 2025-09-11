package db

import (
	"context"
	"database/sql"
	"testing"
	"time"

	"github.com/stretchr/testify/require"
	"github.com/thobbiz/Stockey/utilities"
)

func createRandomCustomer(t *testing.T) Customer {
	arg := CreateCustomerParams{
		Name: utilities.RandomName(),
		Phone: sql.NullInt64{
			Int64: utilities.RandomInt(10000000, 10000000000),
			Valid: true,
		},
	}

	customer, err := testQueries.CreateCustomer(context.Background(), arg)
	require.NoError(t, err)
	require.NotEmpty(t, customer)

	require.Equal(t, arg.Name, customer.Name)
	require.Equal(t, arg.Phone, customer.Phone)

	require.NotZero(t, customer.ID)
	require.NotZero(t, customer.CreatedAt)

	return customer
}

func TestCreateCustomer(t *testing.T) {
	createRandomProduct(t)
}

func TestGetCustomer(t *testing.T) {
	customer1 := createRandomCustomer(t)
	customer2, err := testQueries.GetCustomer(context.Background(), customer1.ID)

	require.NoError(t, err)
	require.NotEmpty(t, customer2)

	require.Equal(t, customer1.ID, customer2.ID)
	require.Equal(t, customer1.Name, customer2.Name)
	require.Equal(t, customer1.Phone, customer2.Phone)
	require.WithinDuration(t, customer1.CreatedAt, customer2.CreatedAt, time.Second)
}

func TestUpdateCustomer(t *testing.T) {
	customer1 := createRandomCustomer(t)
	x := customer1.Phone
	arg := UpdateCustomerParams{
		ID: customer1.ID,
		Phone: sql.NullInt64{
			Int64: utilities.RandomInt(10000000, 10000000000),
			Valid: true,
		},
	}

	customer2, err := testQueries.UpdateCustomer(context.Background(), arg)
	require.NoError(t, err)
	require.NotEmpty(t, customer2)

	require.Equal(t, customer1.ID, customer2.ID)
	require.Equal(t, customer1.Name, customer2.Name)
	require.NotEqual(t, x, customer2.Phone)
	require.WithinDuration(t, customer1.CreatedAt, customer2.CreatedAt, time.Second)
}

func TestDeleteCustomer(t *testing.T) {
	customer1 := createRandomCustomer(t)
	err := testQueries.DeleteCustomer(context.Background(), customer1.ID)
	require.NoError(t, err)

	customer2, err := testQueries.GetCustomer(context.Background(), customer1.ID)
	require.Error(t, err)
	require.EqualError(t, err, sql.ErrNoRows.Error())
	require.Empty(t, customer2)
}

func TestListCustomers(t *testing.T) {
	for i := 0; i < 10; i++ {
		createRandomCustomer(t)
	}

	arg := ListCustomersParams{
		Limit:  5,
		Offset: 5,
	}

	customers, err := testQueries.ListCustomers(context.Background(), arg)
	require.NoError(t, err)
	require.Len(t, customers, 5)

	for _, customer := range customers {
		require.NotEmpty(t, customer)
	}
}

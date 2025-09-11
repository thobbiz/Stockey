package db

import (
	"context"
	"testing"

	"github.com/stretchr/testify/require"
	"github.com/thobbiz/Stockey/utilities"
)

func createRandomOrderProduct(t *testing.T) OrderProduct {
	order := createRandomOrder(t)
	product := createRandomProduct(t)
	arg := CreateOrderProductParams{
		OrderID:   order.ID,
		ProductID: product.ID,
		Price:     product.SellingPrice,
		Quantity:  utilities.RandomQuantity(),
	}

	order_product, err := testQueries.CreateOrderProduct(context.Background(), arg)
	require.NoError(t, err)
	require.NotEmpty(t, order)

	require.Equal(t, arg.OrderID, order_product.OrderID)
	require.Equal(t, arg.ProductID, order_product.ProductID)
	require.Equal(t, arg.Price, order_product.Price)
	require.Equal(t, arg.Quantity, order_product.Quantity)

	return order_product
}

func TestCreateOrderProduct(t *testing.T) {
	createRandomOrderProduct(t)
}

func TestGetOrderProduct(t *testing.T) {
	order_product1 := createRandomOrderProduct(t)
	arg := GetOrderProductParams{
		OrderID:   order_product1.OrderID,
		ProductID: order_product1.ProductID,
	}
	order_product2, err := testQueries.GetOrderProduct(context.Background(), arg)

	require.NoError(t, err)
	require.NotEmpty(t, order_product2)

	require.Equal(t, order_product1.OrderID, order_product2.OrderID)
	require.Equal(t, order_product2.ProductID, order_product2.ProductID)
	require.Equal(t, order_product1.Price, order_product2.Price)
}

func TestListOrderProducts(t *testing.T) {
	for i := 0; i < 10; i++ {
		createRandomOrderProduct(t)
	}

	arg := ListOrderProductsParams{
		Limit:  5,
		Offset: 5,
	}

	order_products, err := testQueries.ListOrderProducts(context.Background(), arg)
	require.NoError(t, err)
	require.Len(t, order_products, 5)

	for _, order_product := range order_products {
		require.NotEmpty(t, order_product)
	}
}

package db

import (
	"context"
	"database/sql"
	"fmt"
)

type Store struct {
	*Queries
	db *sql.DB
}

func NewStore(db *sql.DB) *Store {
	return &Store{
		db:      db,
		Queries: New(db),
	}
}

func (store *Store) execTX(ctx context.Context, fn func(*Queries) error) error {
	tx, err := store.db.BeginTx(ctx, nil)
	if err != nil {
		return err
	}

	q := New(tx)
	err = fn(q)
	if err != nil {
		if rbErr := tx.Rollback(); rbErr != nil {
			return fmt.Errorf("tx err: %v, rb err: %v", err, rbErr)
		}
		return err
	}

	return tx.Commit()
}

type OrderTxParams struct {
	CustomerID    sql.NullInt64
	PaymentMethod PaymentMethod
	OrderProducts []CreateOrderProductInput
	Comment       sql.NullString
}

type CreateOrderProductInput struct {
	ProductID int64 `json:"product_id"`
	Price     int64 `json:"price"`
	Quantity  int64 `json:"quantity"`
}

type OrderProductParams struct {
	OrderID   int64 `json:"order_id"`
	ProductID int64 `json:"product_id"`
	Price     int64 `json:"price"`
	Quantity  int64 `json:"qunatity"`
}

type OrderTxResult struct {
	Order         Order `json:"order"`
	OrderProducts []OrderProduct
	Entry         []Entry `json:"entry"`
}

func (store *Store) OrderTx(ctx context.Context, arg OrderTxParams) (OrderTxResult, error) {
	var result OrderTxResult

	err := store.execTX(ctx, func(q *Queries) error {
		var err error

		// Create order
		result.Order, err = q.CreateOrder(ctx, CreateOrderParams{
			CustomerID:  arg.CustomerID,
			TotalAmount: calculateTotal(arg.OrderProducts),
			Comment:     arg.Comment,
		})
		if err != nil {
			return err
		}

		for _, orderProduct := range arg.OrderProducts {

			// Create entry
			fmt.Printf("create entry for product %d\n", orderProduct.ProductID)
			Entry, err := q.CreateEntry(ctx, CreateEntryParams{
				ProductID: orderProduct.ProductID,
				Quantity:  -orderProduct.Quantity,
			})
			if err != nil {
				return err
			}
			// Add Entry to result
			result.Entry = append(result.Entry, Entry)

			//Check if product exists
			product, err := q.GetProductForUpdate(ctx, orderProduct.ProductID)
			if err != nil {
				if err == sql.ErrNoRows {
					return fmt.Errorf("product %d not found", orderProduct.ProductID)
				}
				return fmt.Errorf("failed to check product availability: %w", err)
			}

			// Verify if there is Sufficient Products
			if orderProduct.Quantity > product.Quantity {
				return fmt.Errorf("insufficient quantity for product %d: need %d, have %d", orderProduct.ProductID, orderProduct.Quantity, product.Quantity)
			}

			// Add orderproduct to table
			op, err := q.CreateOrderProduct(ctx, CreateOrderProductParams{
				OrderID:   result.Order.ID,
				ProductID: orderProduct.ProductID,
				Price:     orderProduct.Price,
				Quantity:  orderProduct.Quantity,
			})
			if err != nil {
				return err
			}
			// Append OrderProduct to result
			result.OrderProducts = append(result.OrderProducts, op)

			// Remove quantity from inventory
			_, err = removeQuantity(ctx, q, orderProduct.ProductID, orderProduct.Quantity)
			if err != nil {
				return err
			}
		}

		result.Order, err = q.UpdatePaymentMethod(ctx,
			UpdatePaymentMethodParams{
				PaymentMethod: arg.PaymentMethod,
				ID:            result.Order.ID,
			})
		if err != nil {
			return err
		}

		result.Order, err = q.UpdateOrderStatus(ctx,
			UpdateOrderStatusParams{
				OrderStatus: OrderStatusCompleted,
				ID:          result.Order.ID,
			})
		if err != nil {
			return err
		}

		return nil
	})

	return result, err
}

func removeQuantity(
	ctx context.Context,
	q *Queries,
	productId int64,
	quantity int64,
) (product Product, err error) {
	product, err = q.AddQuantity(ctx, AddQuantityParams{
		ID:     productId,
		Amount: -quantity,
	})
	if err != nil {
		return
	}
	return
}

func calculateTotal(orderProducts []CreateOrderProductInput) int64 {
	var total int64
	for _, product := range orderProducts {
		total += product.Price * product.Quantity
	}
	return total
}

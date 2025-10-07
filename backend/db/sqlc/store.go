package db

import (
	"context"
	"database/sql"
	"fmt"
	"sort"
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
	OrderInput    []OrderInput
	Comment       sql.NullString
}

type OrderInput struct {
	ProductID int64 `json:"product_id"`
	Quantity  int64 `json:"quantity"`
}

type OrderProductParams struct {
	OrderID   int64 `json:"order_id"`
	ProductID int64 `json:"product_id"`
	Price     int64 `json:"price"`
	Quantity  int64 `json:"quantity"`
}

type OrderTxResult struct {
	Order         Order `json:"order"`
	OrderProducts []OrderProduct
	Entry         []Entry `json:"entry"`
}

var txKey = struct{}{}

func (store *Store) OrderTx(ctx context.Context, arg OrderTxParams) (OrderTxResult, error) {
	var result OrderTxResult

	err := store.execTX(ctx, func(q *Queries) error {
		var err error
		txName := ctx.Value(txKey)

		var total int64
		for _, productInput := range arg.OrderInput {
			product, err := q.GetProduct(ctx, productInput.ProductID)
			if err != nil {
				return err
			}
			total += product.SellingPrice * productInput.Quantity
		}

		// Create order
		fmt.Println(txName, "create order")
		result.Order, err = q.CreateOrder(ctx, CreateOrderParams{
			CustomerID:  arg.CustomerID,
			TotalAmount: total,
			Comment:     arg.Comment,
		})
		if err != nil {
			return err
		}

		sortedInputs := make([]OrderInput, len(arg.OrderInput))
		copy(sortedInputs, arg.OrderInput)
		sort.Slice(sortedInputs, func(i, j int) bool {
			return sortedInputs[i].ProductID < sortedInputs[j].ProductID
		})

		for _, input := range sortedInputs {

			// Create entry
			fmt.Printf("%v create entry for product %d\n", txName, input.ProductID)
			Entry, err := q.CreateEntry(ctx, CreateEntryParams{
				ProductID: input.ProductID,
				Quantity:  -input.Quantity,
			})
			if err != nil {
				return err
			}
			// Add Entry to result
			result.Entry = append(result.Entry, Entry)

			//Check if product exists
			product, err := q.GetProductForUpdate(ctx, input.ProductID)
			if err != nil {
				if err == sql.ErrNoRows {
					return fmt.Errorf("product %d not found", input.ProductID)
				}
				return fmt.Errorf("failed to check product availability: %w", err)
			}

			// Verify if there is Sufficient Products
			if input.Quantity > product.Quantity {
				return fmt.Errorf("insufficient quantity for product %d: need %d, have %d", input.ProductID, input.Quantity, product.Quantity)
			}

			// Add orderproduct to table
			orderProduct, err := q.CreateOrderProduct(ctx, CreateOrderProductParams{
				OrderID:   result.Order.ID,
				ProductID: input.ProductID,
				Price:     product.SellingPrice,
				Quantity:  input.Quantity,
			})
			if err != nil {
				return err
			}
			// Append OrderProduct to result
			result.OrderProducts = append(result.OrderProducts, orderProduct)

			// Remove quantity from inventory
			_, err = removeQuantity(ctx, q, input.ProductID, input.Quantity)
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

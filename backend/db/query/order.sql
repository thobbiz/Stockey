-- name: CreateOrder :one
INSERT INTO orders (
  customer_id,
  total_amount,
  comment
) VALUES (
  $1, $2, $3
)
RETURNING *;

-- name: GetOrder :one
SELECT * FROM orders
WHERE id = $1 LIMIT 1;

-- name: ListOrders :many
SELECT * FROM orders
ORDER BY id LIMIT $1 OFFSET $2;

-- name: DeleteOrder :exec
DELETE FROM orders WHERE id = $1;
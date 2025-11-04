-- name: CreateOrderProduct :one
INSERT INTO order_products (
  order_id,
  product_id,
  price,
  quantity
) VALUES (
  $1, $2, $3, $4
)
RETURNING *;

-- name: GetOrderProduct :one
SELECT * FROM order_products
WHERE order_id = $1 AND product_id = $2 LIMIT 1;

-- name: GetOrderProducts :many
SELECT * FROM order_products
WHERE order_id = $1;

-- name: ListOrderProducts :many
SELECT * FROM order_products
ORDER BY order_id, product_id LIMIT $1 OFFSET $2;

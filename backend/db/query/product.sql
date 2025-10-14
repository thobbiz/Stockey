-- name: CreateProduct :one
INSERT INTO products (
  owner,
  name,
  cost_price,
  selling_price,
  quantity,
  unit,
  description
) VALUES (
  $1, $2, $3, $4, $5, $6, $7
)
RETURNING *;

-- name: GetProduct :one
SELECT * FROM products
WHERE id = $1 LIMIT 1;

-- name: GetProductForUpdate :one
SELECT * FROM products
WHERE id = $1 LIMIT 1
FOR NO KEY UPDATE;

-- name: ListProducts :many
SELECT * FROM products
ORDER BY id LIMIT $1 OFFSET $2;

-- name: AddQuantity :one
UPDATE products SET quantity = quantity + sqlc.arg(amount) WHERE id = sqlc.arg(id) RETURNING *;

-- name: UpdateSellingPrice :one
UPDATE products 
SET selling_price = sqlc.arg(selling_price)
WHERE id = sqlc.arg(id)
RETURNING *;

-- name: UpdateCostPrice :one
UPDATE products 
SET cost_price = sqlc.arg(cost_price)
WHERE id = sqlc.arg(id)
RETURNING *;

-- name: DeleteProduct :exec
DELETE FROM products WHERE id = $1;
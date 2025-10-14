CREATE TYPE "order_status" AS ENUM (
  'pending',
  'completed',
  'cancelled'
);

CREATE TYPE "payment_method" AS ENUM (
  'cash',
  'bank_transfer',
  'debit_card',
  'debt',
  'not_selected'
);

CREATE TABLE "products" (
  "id" BIGSERIAL PRIMARY KEY,
  "owner" varchar NOT NULL,
  "name" varchar NOT NULL,
  "cost_price" bigint NOT NULL,
  "selling_price" bigint NOT NULL,
  "quantity" bigint NOT NULL,
  "unit" varchar NOT NULL,
  "description" varchar,
  "created_at" timestamptz NOT NULL DEFAULT 'now()'
);

CREATE TABLE "entries" (
  "id" BIGSERIAL PRIMARY KEY,
  "product_id" bigint NOT NULL,
  "quantity" bigint NOT NULL,
  "created_at" timestamptz NOT NULL DEFAULT 'now()'
);

CREATE TABLE "orders" (
  "id" BIGSERIAL PRIMARY KEY,
  "customer_id" bigint,
  "total_amount" bigint NOT NULL,
  "order_status" order_status NOT NULL DEFAULT 'pending',
  "payment_method" payment_method NOT NULL DEFAULT 'not_selected',
  "comment" varchar,
  "created_at" timestamptz NOT NULL DEFAULT 'now()'
);

CREATE TABLE "order_products" (
  "order_id" bigint NOT NULL,
  "product_id" bigint NOT NULL,
  "price" bigint NOT NULL,
  "quantity" bigint NOT NULL,
  PRIMARY KEY ("order_id", "product_id")
);

CREATE TABLE "customers" (
  "id" BIGSERIAL PRIMARY KEY,
  "name" varchar NOT NULL,
  "phone" bigint,
  "created_at" timestamptz NOT NULL DEFAULT 'now()'
);

CREATE INDEX ON "products" ("owner");

CREATE INDEX ON "products" ("name");

CREATE INDEX ON "entries" ("product_id");

CREATE INDEX ON "orders" ("customer_id");

CREATE INDEX ON "orders" ("created_at");

CREATE INDEX ON "customers" ("name");

ALTER TABLE "entries" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

ALTER TABLE "orders" ADD FOREIGN KEY ("customer_id") REFERENCES "customers" ("id");

ALTER TABLE "order_products" ADD FOREIGN KEY ("order_id") REFERENCES "orders" ("id");

ALTER TABLE "order_products" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

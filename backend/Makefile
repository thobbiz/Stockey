postgres:
	docker run --name postgres -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -d postgres

createdb:
	docker exec -it postgres createdb --username=root --owner=root stockey

dropdb:
	docker exec -it postgres dropdb stockey

migrateup:
	migrate -path db/migration -database "postgresql://root:secret@localhost:5432/stockey?sslmode=disable" -verbose up


migrateup1:
	migrate -path db/migration -database "postgresql://root:secret@localhost:5432/stockey?sslmode=disable" -verbose up 1


migratedown:
	migrate -path db/migration -database "postgresql://root:secret@localhost:5432/stockey?sslmode=disable" -verbose down

migratedown1:
	migrate -path db/migration -database "postgresql://root:secret@localhost:5432/stockey?sslmode=disable" -verbose down 1


test:
	go test -v -cover ./...

sqlc:
	sqlc generate

server:
	go run main.go

.PHONY: postgres createdb dropdb migrateup migratedown migrateup1 migratedown1 sqlc test server
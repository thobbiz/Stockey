package main

import (
	"database/sql"
	"log"

	_ "github.com/lib/pq"
	"github.com/thobbiz/Stockey/api"
	db "github.com/thobbiz/Stockey/db/sqlc"
)

const (
	dbDriver      = "postgres"
	dbSource      = "postgresql://root:secret@localhost:5432/stockey?sslmode=disable"
	serverAddress = "0.0.0.0:5000"
)

func main() {
	conn, err := sql.Open(dbDriver, dbSource)
	if err != nil {
		log.Fatal("Cannot connect to database!", err)
	}

	store := db.NewStore(conn)
	server := api.NewServer(store)

	err = server.Start(serverAddress)
	if err != nil {
		log.Fatal("cannot create server: ", err)
	}
}

package api

import (
	"github.com/gin-gonic/gin"
	db "github.com/thobbiz/Stockey/db/sqlc"
)

type Server struct {
	store  *db.Store
	router *gin.Engine
}

func NewServer(store *db.Store) *Server {
	server := &Server{
		store: store,
	}
	router := gin.Default()

	router.POST("/products", server.createProduct)
	router.GET("/products/:id", server.getProduct)
	router.GET("/products", server.listAccounts)

	router.POST("/customers", server.createCustomer)
	router.GET("/customers/:id", server.getCustomer)
	router.GET("/customers", server.listCustomers)

	router.POST("/orders", server.createOrder)

	server.router = router
	return server
}

func (server *Server) Start(address string) error {
	return server.router.Run(address)
}

func errorResponse(err error) gin.H {
	return gin.H{"error": err.Error()}
}

package api

import (
	"database/sql"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
	db "github.com/thobbiz/Stockey/db/sqlc"
)

type orderRequest struct {
	CustomerID    int64           `json:"customer_id"`
	PaymentMethod string          `json:"payment_method" binding:"required"`
	OrderInput    []db.OrderInput `json:"order_input" binding:"required,min=1"`
	Comment       string          `json:"comment"`
}

func (server *Server) createOrder(ctx *gin.Context) {
	var req orderRequest
	validCustomer := false
	validComment := false
	if err := ctx.ShouldBindJSON(&req); err != nil {
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}

	if req.CustomerID != 0 {
		validCustomer = true
	}

	if req.Comment != "" {
		validComment = true
	}

	arg := db.OrderTxParams{
		CustomerID:    sql.NullInt64{Int64: req.CustomerID, Valid: validCustomer},
		PaymentMethod: db.PaymentMethod(req.PaymentMethod),
		OrderInput:    req.OrderInput,
		Comment:       sql.NullString{String: req.Comment, Valid: validComment},
	}

	result, err := server.store.OrderTx(ctx, arg)
	if err != nil {
		fmt.Println("This is the error %w", err)
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}

	ctx.JSON(http.StatusOK, result)
}

// func (server *Server) validProduct(ctx *gin.Context, accountID int64, currency string) (db.Product, bool) {
// 	account, err := server.store.GetProduct(ctx, accountID)
// 	if err != nil {
// 		if err == sql.ErrNoRows {
// 			ctx.JSON(http.StatusNotFound, errorResponse(err))
// 			return account, false
// 		}
// 		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
// 		return account, false
// 	}

// 	return account, true
// }

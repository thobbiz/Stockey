package api

import (
	"database/sql"
	"errors"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
	db "github.com/thobbiz/Stockey/db/sqlc"
)

type createOrderRequest struct {
	CustomerID    int64           `json:"customer_id"`
	PaymentMethod string          `json:"payment_method" binding:"required"`
	OrderInput    []db.OrderInput `json:"order_input" binding:"required,min=1"`
	Comment       string          `json:"comment"`
}

type getOrderRequest struct {
	OrderID int64 `uri:"id" binding:"required,min=1"`
}

type listOrderRequest struct {
	PageID   int32 `form:"page_id" binding:"required,min=1"`
	PageSize int32 `form:"page_size" binding:"required,min=5,max=10"`
}

func (server *Server) createOrder(ctx *gin.Context) {
	var req createOrderRequest
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

func (server *Server) getOrder(ctx *gin.Context) {
	var req getOrderRequest
	if err := ctx.ShouldBindUri(&req); err != nil {
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}

	order, err := server.store.GetOrder(ctx, req.OrderID)
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			ctx.JSON(http.StatusNotFound, errorResponse(err))
			return
		}
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
	}

	ctx.JSON(http.StatusOK, order)
}

func (server *Server) listOrders(ctx *gin.Context) {
	var req listOrderRequest
	if err := ctx.ShouldBindQuery(&req); err != nil {
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}

	arg := db.ListOrdersParams{
		Limit:  req.PageSize,
		Offset: (req.PageID - 1) * req.PageSize,
	}

	orders, err := server.store.ListOrders(ctx, arg)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}

	ctx.JSON(http.StatusOK, orders)
}

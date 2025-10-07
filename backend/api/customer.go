package api

import (
	"database/sql"
	"net/http"

	"github.com/gin-gonic/gin"
	db "github.com/thobbiz/Stockey/db/sqlc"
)

type createCustomerRequest struct {
	Name  string `json:"name" binding:"required"`
	Phone int64  `json:"phone_no"`
}

func (server *Server) createCustomer(ctx *gin.Context) {
	var req createCustomerRequest
	if err := ctx.ShouldBindJSON(&req); err != nil {
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}

	valid := false
	if req.Phone != 0 {
		valid = true
	}

	arg := db.CreateCustomerParams{
		Name:  req.Name,
		Phone: sql.NullInt64{Int64: req.Phone, Valid: valid},
	}

	product, err := server.store.CreateCustomer(ctx, arg)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}

	ctx.JSON(http.StatusOK, product)
}

type getCustomerRequest struct {
	ID int64 `uri:"id" binding:"required,min=1"`
}

func (server *Server) getCustomer(ctx *gin.Context) {
	var req getCustomerRequest
	if err := ctx.ShouldBindUri(&req); err != nil {
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}

	product, err := server.store.GetCustomer(ctx, req.ID)
	if err != nil {
		if err == sql.ErrNoRows {
			ctx.JSON(http.StatusNotFound, errorResponse(err))
			return
		}
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}
	ctx.JSON(http.StatusOK, product)
}

type listCustomerRequest struct {
	PageID   int32 `form:"page_id" binding:"required,min=1"`
	PageSize int32 `form:"page_size" binding:"required,min=5,max=10"`
}

func (server *Server) listCustomers(ctx *gin.Context) {
	var req listCustomerRequest
	if err := ctx.ShouldBindQuery(&req); err != nil {
		ctx.JSON(http.StatusBadRequest, errorResponse(err))
		return
	}

	arg := db.ListCustomersParams{
		Limit:  req.PageSize,
		Offset: (req.PageID - 1) * req.PageSize,
	}
	customers, err := server.store.ListCustomers(ctx, arg)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, errorResponse(err))
		return
	}

	ctx.JSON(http.StatusOK, customers)
}

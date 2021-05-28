package com.ejemplo.ProyectoGrupo.Entities;

import java.sql.Date;
import java.util.ArrayList;

import javax.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({
	"id",
	"order_id",
	"product_id",
	"quantity",
	"unit_price",
	"discount",
	"status_id",
	"date_allocated",
	"purchase_order_id",
	"inventory_id",	
	"producto"//--

})

public class Order_detail {

	private	Integer	id	;
	private	Integer	order_id	;
	private	Integer	product_id	;
	private	Float	quantity	;
	private	Float	unit_price	;
	private	Float	discount	;
	private	Integer	status_id	;
	private	Date	date_allocated	;
	private	Integer	purchase_order_id	;
	private	Integer	inventory_id	;
	private ArrayList<Product> producto;


	public Order_detail () {
		
	}
	
public Order_detail (
		Integer	id	,
		Integer	order_id	,
		Integer	product_id	,
		Float	quantity	,
		Float	unit_price	,
		Float	discount	,
		Integer	status_id	,
		Date	date_allocated	,
		Integer	purchase_order_id	,
		Integer	inventory_id	
) {
	this.	id	=	id	;
	this.	order_id	=	order_id	;
	this.	product_id	=	product_id	;
	this.	quantity	=	quantity	;
	this.	unit_price	=	unit_price	;
	this.	discount	=	discount	;
	this.	status_id	=	status_id	;
	this.	date_allocated	=	date_allocated	;
	this.	purchase_order_id	=	purchase_order_id	;
	this.	inventory_id	=	inventory_id	;

	}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public Integer getOrder_id() {
	return order_id;
}

public void setOrder_id(Integer order_id) {
	this.order_id = order_id;
}

public Integer getProduct_id() {
	return product_id;
}

public void setProduct_id(Integer product_id) {
	this.product_id = product_id;
}

public Float getQuantity() {
	return quantity;
}

public void setQuantity(Float quantity) {
	this.quantity = quantity;
}

public Float getUnit_price() {
	return unit_price;
}

public void setUnit_price(Float unit_price) {
	this.unit_price = unit_price;
}

public Float getDiscount() {
	return discount;
}

public void setDiscount(Float discount) {
	this.discount = discount;
}

public Integer getStatus_id() {
	return status_id;
}

public void setStatus_id(Integer status_id) {
	this.status_id = status_id;
}

public Date getDate_allocated() {
	return date_allocated;
}

public void setDate_allocated(Date date_allocated) {
	this.date_allocated = date_allocated;
}

public Integer getPurchase_order_id() {
	return purchase_order_id;
}

public void setPurchase_order_id(Integer purchase_order_id) {
	this.purchase_order_id = purchase_order_id;
}

public Integer getInventory_id() {
	return inventory_id;
}

public void setInventory_id(Integer inventory_id) {
	this.inventory_id = inventory_id;
}

public ArrayList<Product> getProducto() {
	return producto;
}

public void setProducto(ArrayList<Product> producto) {
	this.producto = producto;
}

@Override
public String toString() {
	return "Order_detail [" + (id != null ? "id=" + id + ", " : "")
			+ (order_id != null ? "order_id=" + order_id + ", " : "")
			+ (product_id != null ? "product_id=" + product_id + ", " : "")
			+ (quantity != null ? "quantity=" + quantity + ", " : "")
			+ (unit_price != null ? "unit_price=" + unit_price + ", " : "")
			+ (discount != null ? "discount=" + discount + ", " : "")
			+ (status_id != null ? "status_id=" + status_id + ", " : "")
			+ (date_allocated != null ? "date_allocated=" + date_allocated + ", " : "")
			+ (purchase_order_id != null ? "purchase_order_id=" + purchase_order_id + ", " : "")
			+ (inventory_id != null ? "inventory_id=" + inventory_id + ", " : "")
			+ (producto != null ? "producto=" + producto : "") + "]";
}

}

package com.ejemplo.ProyectoGrupo.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.ejemplo.ProyectoGrupo.DBFactory.DBFactory;
import com.ejemplo.ProyectoGrupo.Entities.Order_detail;
import com.ejemplo.ProyectoGrupo.Entities.Product;

public class Order_detailsModel {
	
	Connection conexion = null;

    public Order_detailsModel() throws SQLException {
	DataSource ds = DBFactory.getMySQLDataSource();
	conexion = ds.getConnection();
    }

    public Order_detail read(Integer id) {
	Order_detail detalle_orden = null;
	Statement sentencia = null;
	ArrayList<Product> producto = null;


	String sql = "	SELECT	`id`,"		
		+"`order_id`,"		
		+"`product_id`,"		
		+"`quantity`,"		
		+"`unit_price`,"		
		+"`discount`,"		
		+"`status_id`,"		
		+"`date_allocated`,"		
		+"`purchase_order_id`,"		
		+"`inventory_id`"	
		+ "FROM order_details "
		+"WHERE	id	="+id	;

     try {
    sentencia = conexion.createStatement();
    ResultSet rs = sentencia.executeQuery(sql);
    while (rs.next()) { // Si hay un pedido que existe
    detalle_orden = new Order_detail(
			rs.getInt("id"),
			rs.getInt("order_id"),
			rs.getInt("product_id"),
			rs.getFloat("quantity"),
			rs.getFloat("unit_price"),
			rs.getFloat("discount"),
			rs.getInt("status_id"),
			rs.getDate("date_allocated"),
			rs.getInt("purchase_order_id"),
			rs.getInt("inventory_id"));
    
    ProductsModel detalleproducto = new ProductsModel();
    producto = detalleproducto.lista("id = "+rs.getInt("product_id"), null, null);
    detalle_orden.setProducto(producto);
    };
    
     } catch (SQLException e) {
 	    System.err.println("Error en read de detalles de orden: " + e.getMessage());
 	    return null;
 	}
    
 	return detalle_orden;	
 	}
    
    
    
    
    
    /**
     * 
     * @param pedido
     * @return Devuelve el id del registro recien insertado
     */

public Integer insert(Order_detail pedido) throws  SQLException {
	Integer id = null;
	PreparedStatement ps = null;
	String sql = "INSERT INTO order_details ( "	
			+" `id `, "		
			+"`order_id`,"		
			+"`product_id`,"		
			+"`quantity`,"		
			+"`unit_price`,"		
			+"`discount`,"		
			+"`status_id`,"		
			+"`date_allocated`,"		
			+"`purchase_order_id`,"		
			+"`inventory_id	`,"			
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	try {
	    ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1,pedido.getId());
	    ps.setInt(2,pedido.getOrder_id());
	    ps.setInt(3,pedido.getProduct_id());
	    ps.setFloat(4,pedido.getQuantity());
	    ps.setFloat(5,pedido.getUnit_price());
	    ps.setFloat(6,pedido.getDiscount());
	    ps.setInt(7,pedido.getStatus_id());
	    ps.setDate(8,pedido.getDate_allocated());
	    ps.setInt(9,pedido.getPurchase_order_id());
	    ps.setInt(10,pedido.getInventory_id());
	    if (ps.executeUpdate() > 0) {
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
		    id = rs.getInt(1);
		}
	    }

	} catch (SQLException e) {
	    System.err.println("Error al insertar Order: " + e.getMessage());
	    throw e;
	}

	return id;
   }



public Boolean delete(Integer idpedido) throws SQLException {
	Boolean resultado = false;

	PreparedStatement ps = null;
	String sql = "DELETE FROM products where id = ?";
	try {
	    ps = conexion.prepareStatement(sql);

	    ps.setInt(1, idpedido);

	    resultado = (ps.executeUpdate() > 0);

	} catch (SQLException e) {
	    System.err.println("Error al borrar Pedido: " + e.getMessage());
	    throw e;
	}

	return resultado;
    }

    public Boolean update(Order_detail pedido) throws SQLException  {
	Boolean resultado = false;

	PreparedStatement ps = null;
	String sql = "UPDATE products set "
		+ "id = ?, "
		+ "order_id = ?, "
		+ "product_id = ?, "
		+ "quantity = ?, "
		+ "unit_price = ?, "
		+ "discount  = ?, "
		+ "status_id= ?, "
		+ "date_allocated	 = ?, "
		+ "purchase_order_id = ?, "
		+ "inventory_id= ?, "
		+ "where id = ?";
	
	try {
	    ps = conexion.prepareStatement(sql);
	    ps.setInt(1,pedido.getId());
	    ps.setInt(2,pedido.getOrder_id());
	    ps.setInt(3,pedido.getProduct_id());
	    ps.setFloat(4,pedido.getQuantity());
	    ps.setFloat(5,pedido.getUnit_price());
	    ps.setFloat(6,pedido.getDiscount());
	    ps.setInt(7,pedido.getStatus_id());
	    ps.setDate(8,pedido.getDate_allocated());
	    ps.setInt(9,pedido.getPurchase_order_id());
	    ps.setInt(10,pedido.getInventory_id());

	    resultado = (ps.executeUpdate() > 0);

	} catch (SQLException e) {
	    System.err.println("Error al actualizar detalles de detalles de orden: " + e.getMessage());
	    throw e;
	}

	return resultado;
    }

    
    
    
    
    
    
    
    public ArrayList<Order_detail> lista(String filtro, Integer limite, Integer offset)

    {
	ArrayList<Order_detail> Detalle_orden = new ArrayList<Order_detail>();
	Statement sentencia = null;

	String sql = "SELECT `id`, "
			+"`order_id`,"		
			+"`product_id`,"		
			+"`quantity`,"		
			+"`unit_price`,"		
			+"`discount`,"		
			+"`status_id`,"		
			+"`date_allocated`,"		
			+"`purchase_order_id`,"		
			+"`inventory_id`"		
		    + "FROM order_details ";

	try {
	    if (filtro != null)
		sql += " WHERE " + filtro;
	    if (limite != null)
		sql += " LIMIT " + limite;
	    if (offset != null)
		sql += " OFFSET " + offset;
	    sentencia = conexion.createStatement();
	    ResultSet rs = sentencia.executeQuery(sql);
	    while (rs.next()) { // Si todavía hay un producto lo añado al array
	    	Detalle_orden.add(new Order_detail(
				rs.getInt("id"),
				rs.getInt("order_id"),
				rs.getInt("product_id"),
				rs.getFloat("quantity"),
				rs.getFloat("unit_price"),
				rs.getFloat("discount"),
				rs.getInt("status_id"),
				rs.getDate("date_allocated"),
				rs.getInt("purchase_order_id"),
				rs.getInt("inventory_id")));

	    };
	    
	} catch (SQLException e) {
	    System.err.println("Error en lectura de detalles de Pedido: " + e.getMessage());
	    return null;
	}

	return Detalle_orden;
    }

}


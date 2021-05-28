package com.ejemplo.ProyectoGrupo.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.ejemplo.ProyectoGrupo.DBFactory.DBFactory;
import com.ejemplo.ProyectoGrupo.Entities.Product;



public class ProductsModel {
	
	Connection conexion = null;

    public ProductsModel() throws SQLException {
	DataSource ds = DBFactory.getMySQLDataSource();
	conexion = ds.getConnection();
    }

    public Product read(Integer id) {
	Product producto = null;
	Statement sentencia = null;

    String sql = "SELECT `supplier_ids`,`id`,`product_code`,`product_name`,`description`,"		
		+"`standard_cost`,`list_price`,`reorder_level`,`target_level`,`quantity_per_unit`,`discontinued`,"		
		+"`minimum_reorder_quantity`,`category`,`attachments`"
		+"FROM products "
		+"WHERE id = "+id;

    
    try {
         sentencia = conexion.createStatement();
         ResultSet rs = sentencia.executeQuery(sql);
         while (rs.next()) { // Si hay un producto que existe
	     producto = new Product(
		rs.getString("supplier_ids"),
		rs.getInt("id"),
		rs.getString("product_code"),
		rs.getString("product_name"),
		rs.getString("description"),
		rs.getFloat("standard_cost"),
		rs.getFloat("list_price"),
		rs.getInt("reorder_level"),
		rs.getInt("target_level"),
		rs.getString("quantity_per_unit"),
		rs.getInt("discontinued"),
		rs.getInt("minimum_reorder_quantity"),
		rs.getString("category"),
		rs.getBlob("attachments"));
    };
  
     } catch (SQLException e) {
 	    System.err.println("Error en read de Productos: " + e.getMessage());
 	    return null;
 	}
    
 	return producto;	
 	}
    /**
     * 
     * @param producto
     * @return Devuelve el id del registro recien insertado
     */
    
    
    
    
//-----------------------------------------------------------------------------------------
    
    
    
    
    
    
    
    
    public Integer insert(Product producto) throws  SQLException {
	Integer id = null;
	PreparedStatement ps = null;
	String sql = "INSERT INTO products ("
			+" `supplier_ids`,`id`,`product_code`,"		
			+"`product_name`,`description`,`standard_cost`,`list_price`,"		
			+"`reorder_level`,`target_level`,`quantity_per_unit`,`discontinued`,"		
			+"`minimum_reorder_quantity`,`category`,`attachments`,"		
			+"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	try {
	    ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    ps.setInt(1, producto.getId());
	    ps.setString(2, producto.getProduct_code());
	    ps.setString(3, producto.getProduct_name());
	    ps.setString(4, producto.getDescription());
	    ps.setFloat(5, producto.getStandard_cost());
	    ps.setFloat(6, producto.getList_price());
	    ps.setInt(7, producto.getReorder_level());
	    ps.setInt(8, producto.getTarget_level());
	    ps.setString(9, producto.getQuantity_per_unit());
	    ps.setInt(10, producto.getDiscontinued());
	    ps.setInt(11, producto.getMinimum_reorder_quantity());
	    ps.setString(12, producto.getCategory());
	    ps.setBlob(13, producto.getAttachments());
	    if (ps.executeUpdate() > 0) {
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
		    id = rs.getInt(1);
		}
	    }

	} catch (SQLException e) {
	    System.err.println("Error al insertar Product: " + e.getMessage());
	    throw e;
	}

	return id;
   }



    public Boolean delete(Integer idproducto) throws SQLException {
	Boolean resultado = false;

	PreparedStatement ps = null;
	String sql = "DELETE FROM products where id = ?";
	try {
	    ps = conexion.prepareStatement(sql);

	    ps.setInt(1, idproducto);

	    resultado = (ps.executeUpdate() > 0);

	} catch (SQLException e) {
	    System.err.println("Error al borrar Producto: " + e.getMessage());
	    throw e;
	}

	return resultado;
    }

    public Boolean update(Product producto) throws SQLException  {
	Boolean resultado = false;

	PreparedStatement ps = null;
	String sql = "UPDATE products set "
		+ "supplier_ids = ?,"
		+ "id = ?, "
		+ "product_code = ?, "
		+ "product_name = ?, "
		+ "description = ?, "
		+ "standard_cost = ?, "
		+ "list_price  = ?, "
		+ "reorder_level = ?, "
		+ "target_level = ?, "
		+ "quantity_per_unit = ?, "
		+ "discontinued = ?, "
		+ "minimum_reorder_quantity = ?, "
		+ "category = ?, "
		+ "attachments = ? "
		+ "where id = ?";
	
	try {
	    ps = conexion.prepareStatement(sql);
	    ps.setInt(1, producto.getId());
	    ps.setString(2, producto.getProduct_code());
	    ps.setString(3, producto.getProduct_name());
	    ps.setString(4, producto.getDescription());
	    ps.setFloat(5, producto.getStandard_cost());
	    ps.setFloat(6, producto.getList_price());
	    ps.setInt(7, producto.getReorder_level());
	    ps.setInt(8, producto.getTarget_level());
	    ps.setString(9, producto.getQuantity_per_unit());
	    ps.setInt(10, producto.getDiscontinued());
	    ps.setInt(11, producto.getMinimum_reorder_quantity());
	    ps.setString(12, producto.getCategory());
	    ps.setBlob(13, producto.getAttachments());

	    resultado = (ps.executeUpdate() > 0);

	} catch (SQLException e) {
	    System.err.println("Error al actualizar Producto: " + e.getMessage());
	    throw e;
	}

	return resultado;
    }

    public ArrayList<Product> lista(String filtro, Integer limite, Integer offset)

    {
	ArrayList<Product> producto = new ArrayList<Product>();
	Statement sentencia = null;

	String sql = "SELECT `supplier_ids`,`id`,`product_code`,`product_name`,`description`,"		
			+"`standard_cost`,`list_price`,`reorder_level`,`target_level`,`quantity_per_unit`,`discontinued`,"		
			+"`minimum_reorder_quantity`,`category`,`attachments`"
			+"FROM products";

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
		producto.add(new Product(
			rs.getString("supplier_ids"),
			rs.getInt("id"),
			rs.getString("product_code"),
			rs.getString("product_name"),
			rs.getString("description"),
			rs.getFloat("standard_cost"),
			rs.getFloat("list_price"),
			rs.getInt("reorder_level"),
			rs.getInt("target_level"),
			rs.getString("quantity_per_unit"),
			rs.getInt("discontinued"),
			rs.getInt("minimum_reorder_quantity"),
			rs.getString("category"),
			rs.getBlob("attachments")));
	    };
	} catch (SQLException e) {
	    System.err.println("Error en read de Productos: " + e.getMessage());
	    return null;
	}

	return producto;
    }

}


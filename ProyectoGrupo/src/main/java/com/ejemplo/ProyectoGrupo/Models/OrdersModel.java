package com.ejemplo.ProyectoGrupo.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.ejemplo.ProyectoGrupo.DBFactory.DBFactory;
import com.ejemplo.ProyectoGrupo.Entities.Order;
import com.ejemplo.ProyectoGrupo.Entities.Order_detail;

public class OrdersModel {

	Connection conexion = null;

	public OrdersModel() throws SQLException {
	DataSource ds = DBFactory.getMySQLDataSource();
	conexion = ds.getConnection();
	}

	public Order read(Integer id) {
    Order orden = null;
	Statement sentencia = null;
	ArrayList<Order_detail> listadetallesordenes = null;

    String sql = "SELECT `id`,`employee_id`,`customer_id`,`order_date`,`shipped_date`,`shipper_id`,"
        +"`ship_name`,`ship_address`,`ship_city`,`ship_state_province`,`ship_zip_postal_code`,"
		+"`ship_country_region`,`shipping_fee`,`taxes`,`payment_type`,`paid_date`,`notes`,`tax_rate`,`tax_status_id`,`status_id` "
        +"FROM orders "
		+"WHERE id= "+id;

     try {
    sentencia = conexion.createStatement();
    ResultSet rs = sentencia.executeQuery(sql);
    while (rs.next()) { // Si hay un pedido que existe
    	orden = new Order(
		rs.getInt("id"),
		rs.getInt("employee_id"),
		rs.getInt("customer_id"),
		rs.getDate("order_date"),
		rs.getDate("shipped_date"),
		rs.getInt("shipper_id"),
		rs.getString("ship_name"),
		rs.getString("ship_address"),
		rs.getString("ship_city"),
		rs.getString("ship_state_province"),
		rs.getString("ship_zip_postal_code"),
		rs.getString("ship_country_region"),
		rs.getFloat("shipping_fee"),
		rs.getFloat("taxes"),
		rs.getString("payment_type"),
		rs.getDate("paid_date"),
		rs.getString("notes"),
		rs.getFloat("tax_rate"),
		rs.getInt("tax_status_id"),
		rs.getInt("status_id")
		);
        Order_detailsModel detallespedido = new Order_detailsModel();
        listadetallesordenes = detallespedido.lista("order_id = "+rs.getInt("id"), null, null);
        orden.setLista(listadetallesordenes);
    };
    
    

    
    
     } catch (SQLException e) {
 	    System.err.println("Error en read de Pedidos: " + e.getMessage());
 	    return null;
 	}
    
 	return orden;	
 	}

	/**
	 * 
	 * @param pedido
	 * @return Devuelve el id del registro recien insertado
	 */

	public Integer insert(Order pedido) throws SQLException {
		Integer id = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO orders ( "
				+ "`id`,"
				+ "`employee_id`,"
				+ "`customer_id`,"
				+ "`order_date`,"
				+ "`shipped_date`,"
				+ "`shipper_id`,"
				+ "`ship_name`,"
				+ "`ship_address`,"
				+ "`ship_city`,"
				+ "`ship_state_province`,"
				+ "`ship_zip_postal_code`,"
				+ "`ship_country_region`,"
				+ "`shipping_fee`,"
				+ "`taxes`,"
				+ "`payment_type`,"
				+ "`paid_date`,"
				+ "`notes`,"
				+ "`tax_rate`,"
				+ "`tax_status_id`,"
				+ "`status_id`,"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
		try {
			ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, pedido.getId());
			ps.setInt(2, pedido.getEmployee_id());
			ps.setInt(3, pedido.getCustomer_id());
			ps.setDate(4, pedido.getOrder_date());
			ps.setDate(5, pedido.getShipped_date());
			ps.setInt(6, pedido.getShipper_id());
			ps.setString(7, pedido.getShip_name());
			ps.setString(8, pedido.getShip_address());
			ps.setString(9, pedido.getShip_city());
			ps.setString(10, pedido.getShip_state_province());
			ps.setString(11, pedido.getShip_zip_postal_code());
			ps.setString(12, pedido.getShip_country_region());
			ps.setFloat(13, pedido.getShipping_fee());
			ps.setFloat(14, pedido.getTaxes());
			ps.setString(15, pedido.getPayment_type());
			ps.setDate(16, pedido.getPaid_date());
			ps.setString(17, pedido.getNotes());
			ps.setFloat(18, pedido.getTax_rate());
			ps.setInt(19, pedido.getTax_status_id());
			ps.setInt(20, pedido.getStatus_id());
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
		String sql = "DELETE FROM orders where id = ?";
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

	public Boolean update(Order pedido) throws SQLException {
		Boolean resultado = false;

		PreparedStatement ps = null;
		String sql = "UPDATE products set "
				+ "id = ?, "
				+ "employee_id = ?, "
				+ "customer_id = ?, "
				+ "order_date = ?, "
				+ "shipped_date = ?, "
				+ "shipper_id  = ?, "
				+ "ship_name = ?, "
				+ "ship_address = ?, "
				+ "ship_city = ?, "
				+ "ship_state_province = ?, "
				+ "ship_zip_postal_code = ?, "
				+ "ship_country_region = ?, "
				+ "shipping_fee = ? "
				+ "taxes = ? "
				+ "payment_type = ? "
				+ "paid_date = ? "
				+ "notes = ? "
				+ "tax_rate = ? "
				+ "tax_status_id = ? "
				+ "status_id = ? "
				+ "where id = ?";

		try {
			ps = conexion.prepareStatement(sql);
			ps.setInt(1, pedido.getId());
			ps.setInt(2, pedido.getEmployee_id());
			ps.setInt(3, pedido.getCustomer_id());
			ps.setDate(4, pedido.getOrder_date());
			ps.setDate(5, pedido.getShipped_date());
			ps.setInt(6, pedido.getShipper_id());
			ps.setString(7, pedido.getShip_name());
			ps.setString(8, pedido.getShip_address());
			ps.setString(9, pedido.getShip_city());
			ps.setString(10, pedido.getShip_state_province());
			ps.setString(11, pedido.getShip_zip_postal_code());
			ps.setString(12, pedido.getShip_country_region());
			ps.setFloat(13, pedido.getShipping_fee());
			ps.setFloat(14, pedido.getTaxes());
			ps.setString(15, pedido.getPayment_type());
			ps.setDate(16, pedido.getPaid_date());
			ps.setString(17, pedido.getNotes());
			ps.setFloat(18, pedido.getTax_rate());
			ps.setInt(19, pedido.getTax_status_id());
			ps.setInt(20, pedido.getStatus_id());

			resultado = (ps.executeUpdate() > 0);

		} catch (SQLException e) {
			System.err.println("Error al actualizar Producto: " + e.getMessage());
			throw e;
		}

		return resultado;
	}

	public ArrayList<Order> lista(String filtro, Integer limite, Integer offset)

	{
		ArrayList<Order> Orden = new ArrayList<Order>();
		Statement sentencia = null;

		String sql = "SELECT `id`, "
				+ "`employee_id`,"
				+ "`customer_id`,"
				+ "`order_date`,"
				+ "`shipped_date`,"
				+ "`shipper_id`,"
				+ "`ship_name`,"
				+ "`ship_address`,"
				+ "`ship_city`,"
				+ "`ship_state_province`,"
				+ "`ship_zip_postal_code`,"
				+ "`ship_country_region`,"
				+ "`shipping_fee`,"
				+ "`taxes`,"
				+ "`payment_type`,"
				+ "`paid_date`,"
				+ "`notes`,"
				+ "`tax_rate`,"
				+ "`tax_status_id`,"
				+ "`status_id` "
				+ "FROM orders ";

		try {
			if (filtro != null)
				sql += " WHERE " + filtro;
			if (limite != null)
				sql += " LIMIT " + limite;
			if (offset != null)
				sql += " OFFSET " + offset;
			sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery(sql);
			while (rs.next()) { // Si todavía hay un pedido lo añado al array
				Orden.add(new Order(
						rs.getInt("id"),
						rs.getInt("employee_id"),
						rs.getInt("customer_id"),
						rs.getDate("order_date"),
						rs.getDate("shipped_date"),
						rs.getInt("shipper_id"),
						rs.getString("ship_name"),
						rs.getString("ship_address"),
						rs.getString("ship_city"),
						rs.getString("ship_state_province"),
						rs.getString("ship_zip_postal_code"),
						rs.getString("ship_country_region"),
						rs.getFloat("shipping_fee"),
						rs.getFloat("taxes"),
						rs.getString("payment_type"),
						rs.getDate("paid_date"),
						rs.getString("notes"),
						rs.getFloat("tax_rate"),
						rs.getInt("tax_status_id"),
						rs.getInt("status_id")));
			}
			;
		} catch (SQLException e) {
			System.err.println("Error en read de Pedidos: " + e.getMessage());
			return null;
		}

		return Orden;
	}

}

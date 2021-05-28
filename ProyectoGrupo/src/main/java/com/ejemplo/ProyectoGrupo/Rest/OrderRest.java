package com.ejemplo.ProyectoGrupo.Rest;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ejemplo.ProyectoGrupo.Entities.Order;
import com.ejemplo.ProyectoGrupo.Models.OrdersModel;

@Path("ordenes")
public class OrderRest {
	static OrdersModel orders;

    public OrderRest() {

	try {
	    orders = new OrdersModel();
	} catch (SQLException e) {
	    System.err.println("No puedo abrir la conexion con 'Orden': " + e.getMessage());
	}
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@QueryParam("filter") String filter, 
	                 @QueryParam("limit") Integer limit, 
	                 @QueryParam("offset") Integer offset) {
	Response respuesta = Response.status(Response.Status.NOT_FOUND).build();

	if (orders != null) {
	    ArrayList<Order> listaorden = orders.lista(filter, limit, offset);
	    if (listaorden != null) {
		respuesta = Response.status(Response.Status.OK).entity(listaorden).build();
	    }

	}
	return respuesta;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer id) {
	
	Response respuesta = Response.status(Response.Status.NOT_FOUND).entity("No he encotrado").build();
	
	if (orders != null) {
	    Order orden = orders.read(id);
	    if (orden != null) {
		respuesta = Response.status(Response.Status.OK).entity(orden).build();
	    }
	}
	return respuesta;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Order orden) {
	Integer idorden;
	Response response;
	try {
	    idorden = orders.insert(orden);
	    response = Response.status(Response.Status.CREATED).entity(idorden).build();
	} catch (Exception e) {
	    response = Response.status(Response.Status.CONFLICT).entity("ERROR: " + e.getCause() + " " + e.getMessage())
		    .build();
	}
	return response;
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Order orden) {
	Boolean ordenactualizado;
	Response response;
	try {
	    ordenactualizado = orders.update(orden);
	    response = Response.status(Response.Status.OK).entity(ordenactualizado).build();
	} catch (Exception e) {
	    response = Response.status(Response.Status.NOT_MODIFIED).entity("ERROR: " + e.getCause() + " " + e.getMessage())
		    .build();
	}
	return response;
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
	Boolean ordenactualizado;
	Response response;
	try {
	    ordenactualizado = orders.delete(id);
	    response = Response.status(Response.Status.OK).entity(ordenactualizado).build();
	} catch (Exception e) {
	    response = Response.status(Response.Status.NOT_FOUND).entity("ERROR: " + e.getCause() + " " + e.getMessage())
		    .build();
	}
	return response;
    }
}
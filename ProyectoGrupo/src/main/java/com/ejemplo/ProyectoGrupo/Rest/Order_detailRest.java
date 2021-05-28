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


import com.ejemplo.ProyectoGrupo.Entities.Order_detail;
import com.ejemplo.ProyectoGrupo.Models.Order_detailsModel;

@Path("ordendetalle")
public class Order_detailRest {
	static Order_detailsModel order_detail;

    public Order_detailRest() {

	try {
		order_detail = new Order_detailsModel();
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

	if (order_detail != null) {
	    ArrayList<Order_detail> lista_detalle_orden = order_detail.lista(filter, limit, offset);
	    if (lista_detalle_orden != null) {
		respuesta = Response.status(Response.Status.OK).entity(lista_detalle_orden).build();
	    }

	}
	return respuesta;
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") Integer id) {
	
	Response respuesta = Response.status(Response.Status.NOT_FOUND).entity("No he encotrado").build();
	
	if (order_detail != null) {
	    Order_detail detalle_orden = order_detail.read(id);
	    if (detalle_orden != null) {
		respuesta = Response.status(Response.Status.OK).entity(detalle_orden).build();
	    }
	}
	return respuesta;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Order_detail detalle_orden) {
	Integer id_detalleorden;
	Response response;
	try {
	    id_detalleorden = order_detail.insert(detalle_orden);
	    response = Response.status(Response.Status.CREATED).entity(id_detalleorden).build();
	} catch (Exception e) {
	    response = Response.status(Response.Status.CONFLICT).entity("ERROR: " + e.getCause() + " " + e.getMessage())
		    .build();
	}
	return response;
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Order_detail detalle_orden) {
	Boolean detalle_ordenactualizado;
	Response response;
	try {
	    detalle_ordenactualizado = order_detail.update(detalle_orden);
	    response = Response.status(Response.Status.OK).entity(detalle_ordenactualizado).build();
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
	Boolean detalle_ordenactualizado;
	Response response;
	try {
	    detalle_ordenactualizado = order_detail.delete(id);
	    response = Response.status(Response.Status.OK).entity(detalle_ordenactualizado).build();
	} catch (Exception e) {
	    response = Response.status(Response.Status.NOT_FOUND).entity("ERROR: " + e.getCause() + " " + e.getMessage())
		    .build();
	}
	return response;
    }
}

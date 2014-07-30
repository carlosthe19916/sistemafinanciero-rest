package org.sistemafinanciero.rest.impl;

import java.math.BigInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/agencia")
public class AgenciaRESTService {

	@GET
	@Produces({ "application/xml", "application/json" })
	public Response findAll() {
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findAgencia(@PathParam("id") BigInteger id) {
		return null;
	}

	@PUT
	@Path("/{id}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response updateAgencia(@PathParam("id") BigInteger id) {
		return null;
	}

	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response createAgencia() {
		return null;
	}

	@DELETE
	@Path("/{id}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response updateCaja(@PathParam("id") BigInteger id) {
		return null;
	}

}

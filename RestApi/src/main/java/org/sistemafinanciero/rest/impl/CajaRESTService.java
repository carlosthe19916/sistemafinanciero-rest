/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sistemafinanciero.rest.impl;

import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.dto.VoucherCompraVenta;
import org.sistemafinanciero.entity.dto.VoucherTransferenciaBancaria;
import org.sistemafinanciero.service.nt.CajaServiceNT;

@Path("/caja")
@Stateless
public class CajaRESTService {

	@EJB
	private CajaServiceNT cajaService;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findCaja(@PathParam("id") int id) {
		return null;
	}

	@GET
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	public Response getBovedasOfCaja(@PathParam("id") int id) {
		return null;
	}

	@GET
	@Path("/{id}/detalle")
	@Produces({ "application/xml", "application/json" })
	public Response getDetalleOfCaja(@PathParam("id") int id) {
		return null;
	}

	@GET
	@Path("/{id}/transaccionbovedacaja/enviados")
	@Produces({ "application/xml", "application/json" })
	public Response getTransaccionesBovedaCajaOfCajaEnviados(@PathParam("id") int id) {
		return null;
	}

	@GET
	@Path("/{id}/transaccionbovedacaja/recibidos")
	@Produces({ "application/xml", "application/json" })
	public Response getTransaccionesBovedaCajaOfCajaRecibidos(@PathParam("id") int id) {
		return null;
	}

	@GET
	@Path("/{id}/historial")
	@Produces({ "application/xml", "application/json" })
	public Response getHistorialOfCaja(@PathParam("id") int id, @QueryParam("desde") Long desde, @QueryParam("hasta") Long hasta) {
		return null;
	}

	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response createCaja() {
		return null;
	}

	@POST
	@Path("/{id}/abrir")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response abrirCaja(@PathParam("id") int id) {
		return null;
	}

	@POST
	@Path("/{id}/cerrar")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response cerrarCaja(@PathParam("id") int id) {
		return null;
	}

	@PUT
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response updateCaja(Caja caja) {
		return null;
	}

	@DELETE
	@Path("/{id}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response updateCaja(@PathParam("id") int id) {
		return null;
	}

	@GET
	@Path("{id}/voucherCompraVenta")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response getVoucherCompraVenta(@PathParam("id") BigInteger idTransfereciaCompraVenta) {
		VoucherCompraVenta voucherCompraVenta = cajaService.getVoucherCompraVenta(idTransfereciaCompraVenta);
		return Response.status(Response.Status.OK).entity(voucherCompraVenta).build();
	}
}

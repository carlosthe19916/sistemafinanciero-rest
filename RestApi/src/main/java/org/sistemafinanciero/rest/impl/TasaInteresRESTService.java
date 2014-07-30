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

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
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
import org.sistemafinanciero.service.nt.TasaInteresServiceNT;
import org.sistemafinanciero.service.nt.VariableSistemaServiceNT;

@Path("/tasa")
@Stateless
public class TasaInteresRESTService {
    
	@EJB
	private TasaInteresServiceNT tasaInteresService;
	
	@EJB
	private VariableSistemaServiceNT variableSistemaService;
	
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findCaja(@PathParam("id")int id) {				
		return null;
	}
     
	@GET
	@Path("/ahorro/{idMoneda}")
	@Produces({ "application/xml", "application/json" })
	public Response findTasaAhorroByMoneda(@PathParam("idMoneda")BigInteger idMoneda) {				
		BigDecimal result = tasaInteresService.getTasaInteresCuentaAhorro(idMoneda);
		if(result != null){
			JsonObject model = Json.createObjectBuilder().add("valor", result).build();			
			return Response.status(Response.Status.OK).entity(model).build();
		}			
		else {
			JsonObject model = Json.createObjectBuilder().add("message", "No se encontró resultados").build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}			
	}
	
	@GET
	@Path("/corriente/{idMoneda}")
	@Produces({ "application/xml", "application/json" })
	public Response findTasaCorrienteByMoneda(@PathParam("idMoneda")BigInteger idMoneda) {				
		BigDecimal result = tasaInteresService.getTasaInteresCuentaCorriente(idMoneda);
		if(result != null){
			JsonObject model = Json.createObjectBuilder().add("valor", result).build();			
			return Response.status(Response.Status.OK).entity(model).build();
		}			
		else {
			JsonObject model = Json.createObjectBuilder().add("message", "No se encontró resultados").build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}			
	}
	
	@GET
	@Path("/plazoFijo/{idMoneda}")
	@Produces({ "application/xml", "application/json" })
	public Response findTasaPlazoFijoByMoneda(@PathParam("idMoneda")BigInteger idMoneda) {				
		BigDecimal result = tasaInteresService.getTasaInteresCuentaPlazoFijo(idMoneda, 0, BigDecimal.ZERO);
		if(result != null){
			JsonObject model = Json.createObjectBuilder().add("valor", result).build();			
			return Response.status(Response.Status.OK).entity(model).build();
		}			
		else {
			JsonObject model = Json.createObjectBuilder().add("message", "No se encontró resultados").build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}			
	}
	
	@GET
	@Path("/plazoFijo/{idMoneda}/{periodo}/{monto}")
	@Produces({ "application/xml", "application/json" })
	public Response findTasaPlazoFijoByMonedaPeriodoMonto(@PathParam("idMoneda")BigInteger idMoneda,@PathParam("periodo")int periodo, @PathParam("monto")BigDecimal monto) {				
		BigDecimal result = tasaInteresService.getTasaInteresCuentaPlazoFijo(idMoneda, periodo, monto);
		if(result != null){
			JsonObject model = Json.createObjectBuilder().add("valor", result).build();			
			return Response.status(Response.Status.OK).entity(model).build();
		}			
		else {
			JsonObject model = Json.createObjectBuilder().add("message", "No se encontró resultados").build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}			
	}
	
	@GET
	@Path("/tasaCambio")
	@Produces({ "application/xml", "application/json" })
	public Response getTasaCambio(@QueryParam("idMonedaRecibida") BigInteger idMonedaRecibida,@QueryParam("idMonedaEntregada") BigInteger idMonedaEntregada) {				
		BigDecimal result = variableSistemaService.getTasaCambio(idMonedaRecibida, idMonedaEntregada);
		if(result != null){
			//JsonObject model = Json.createObjectBuilder().add("valor", result).build();			
			return Response.status(Response.Status.OK).entity(result).build();
		}			
		else {
			JsonObject model = Json.createObjectBuilder().add("message", "No se encontró resultados").build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}			
	}
	
	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response createCaja() {				
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
}

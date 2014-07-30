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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.service.nt.VariableSistemaServiceNT;

@Path("/variable")
@Stateless
public class VariableSistemaRESTService {
    
	@EJB
	private VariableSistemaServiceNT variableSistemaService;
	
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findVariable(@PathParam("id")int id) {				
		return null;
	}
     
	@GET
	@Path("/tasaCambio")
	@Produces({ "application/xml", "application/json" })
	public Response findTasaAhorroByMoneda(
			@QueryParam("idMonedaRecibida") BigInteger idMonedaRecibida,
			@QueryParam("idMonedaEntregada") BigInteger idMonedaEntregada){
		BigDecimal tasaCambio = variableSistemaService.getTasaCambio(idMonedaRecibida, idMonedaEntregada);
		if(tasaCambio != null){			
			return Response.status(Response.Status.NOT_FOUND).entity(tasaCambio).build();
		} else {
			JsonObject model = Json.createObjectBuilder().add("message", "No se encontró resultados").build();
			return Response.status(Response.Status.NOT_FOUND).entity(model).build();
		}			
	}
	
}

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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.entity.dto.VoucherTransaccionBovedaCaja;
import org.sistemafinanciero.service.nt.TransaccionInternaServiceNT;

@Path("/transaccionInterna")
@Stateless
public class TransaccionInternaRESTService {
    
	@EJB
	private TransaccionInternaServiceNT transaccionInternaService;
	
	@GET
	@Path("transaccionBovedaCaja/{id}/voucher")
	@Produces({ "application/xml", "application/json" })
	public Response getVoucherTransaccionBovedaCaja(@PathParam("id")BigInteger id) {				
		Response result = null;
		VoucherTransaccionBovedaCaja transaccion = transaccionInternaService.getVoucherTransaccionBovedaCaja(id);
		result = Response.status(Response.Status.OK).entity(transaccion).build();	
		return result;
	}
     
	
}

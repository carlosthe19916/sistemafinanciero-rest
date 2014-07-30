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
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.entity.dto.CajaCierreMoneda;
import org.sistemafinanciero.entity.dto.ResumenOperacionesCaja;
import org.sistemafinanciero.service.nt.CajaServiceNT;

@Path("/historialcaja")
@Stateless
public class HistorialCajaRESTService {
       
    @Resource
    private SessionContext context;
    
    @EJB 
    private CajaServiceNT cajaServiceNT;
    
    @GET
    @Path("{id}/voucherCierreCaja")  
    @Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
    public Response getVoucherCierreCaja(@PathParam("id") BigInteger idHistorial){
    	Set<CajaCierreMoneda> result = cajaServiceNT.getVoucherCierreCaja(idHistorial);    	
		return Response.status(Response.Status.OK).entity(result).build(); 
    }
    
    @GET
    @Path("{id}/resumenCierreCaja")  
    @Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
    public Response getResumenCierreCaja(@PathParam("id") BigInteger idHistorial){
    	ResumenOperacionesCaja result = cajaServiceNT.getResumenOperacionesCaja(idHistorial);
		return Response.status(Response.Status.OK).entity(result).build(); 
    }
}

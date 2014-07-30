package org.sistemafinanciero.rest.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.dto.GenericDetalle;
import org.sistemafinanciero.service.nt.MonedaServiceNT;

@Path("/moneda")
@Stateless
public class MonedaRESTService {
    
    private Logger log;
    
    
    @EJB private MonedaServiceNT monedaService;
    
    @GET
	@Produces({ "application/xml", "application/json" })
    public Response getList(){
    	List<Moneda> list = monedaService.findAll();
    	return Response.status(Response.Status.OK).entity(list).build();
    }
    
    @GET
	@Path("{id}/denominaciones")
	@Produces({ "application/xml", "application/json" })
	public Response getDetalleByMoneda(@PathParam("id") BigInteger idMoneda) {    	     	
		Set<GenericDetalle> result = monedaService.getGenericDenominaciones(idMoneda);
		return Response.status(Response.Status.OK).entity(result).build();
	}
    
  
}

package org.sistemafinanciero.service.nt;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.MonedaDenominacion;
import org.sistemafinanciero.entity.dto.GenericDetalle;

@Remote
public interface MonedaServiceNT extends AbstractServiceNT<Moneda> {

	public List<MonedaDenominacion> getDenominaciones(BigInteger idMoneda);

	public Set<GenericDetalle> getGenericDenominaciones(BigInteger idMoneda);

}

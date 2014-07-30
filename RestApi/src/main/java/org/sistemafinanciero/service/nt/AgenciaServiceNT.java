package org.sistemafinanciero.service.nt;

import java.math.BigInteger;
import java.util.Set;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Agencia;
import org.sistemafinanciero.entity.Caja;

@Remote
public interface AgenciaServiceNT extends AbstractServiceNT<Agencia> {

	public Set<Caja> getCajas(BigInteger idAgencia);

}

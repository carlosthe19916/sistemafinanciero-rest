package org.sistemafinanciero.service.nt;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.VariableSistema;
import org.sistemafinanciero.entity.type.Variable;

@Remote
public interface VariableSistemaServiceNT extends AbstractServiceNT<VariableSistema> {

	public VariableSistema getVariable(Variable variable);

	public BigDecimal getTasaCambio(BigInteger idMonedaRecibida, BigInteger idMonedaEntregada);

}

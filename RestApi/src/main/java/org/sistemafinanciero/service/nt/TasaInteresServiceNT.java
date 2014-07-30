package org.sistemafinanciero.service.nt;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.ejb.Remote;

@Remote
public interface TasaInteresServiceNT extends AbstractServiceNT<TasaInteresServiceNT> {

	public BigDecimal getTasaInteresCuentaPlazoFijo(BigInteger idMoneda, int periodo, BigDecimal monto);

	public BigDecimal getTasaInteresCuentaAhorro(BigInteger idMoneda);

	public BigDecimal getTasaInteresCuentaCorriente(BigInteger idMoneda);

	public BigDecimal getInteresGenerado(BigDecimal monto, int periodo, BigDecimal tasaInteres);

}

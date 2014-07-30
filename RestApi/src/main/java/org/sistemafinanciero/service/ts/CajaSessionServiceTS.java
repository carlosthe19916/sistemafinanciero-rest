package org.sistemafinanciero.service.ts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Beneficiario;
import org.sistemafinanciero.entity.dto.GenericDetalle;
import org.sistemafinanciero.entity.dto.GenericMonedaDetalle;
import org.sistemafinanciero.entity.type.TipoPersona;
import org.sistemafinanciero.entity.type.Tipotransaccioncompraventa;
import org.sistemafinanciero.exception.RollbackFailureException;

@Remote
public interface CajaSessionServiceTS {

	public BigInteger abrirCaja() throws RollbackFailureException;

	public BigInteger cerrarCaja(Set<GenericMonedaDetalle> detalleCaja) throws RollbackFailureException;

	public BigInteger crearAporte(BigInteger idSocio, BigDecimal monto, int mes, int anio, String referencia) throws RollbackFailureException;

	public BigInteger retiroCuentaAporte(BigInteger idSocio) throws RollbackFailureException;

	public BigInteger crearDepositoBancario(String numeroCuenta, BigDecimal monto, String referencia) throws RollbackFailureException;

	public BigInteger crearRetiroBancario(String numeroCuenta, BigDecimal monto, String referencia) throws RollbackFailureException;

	public BigInteger crearCompraVenta(Tipotransaccioncompraventa tipoTransaccion, BigInteger idMonedaRecibido, BigInteger idMonedaEntregado, BigDecimal montoRecibido, BigDecimal montoEntregado, BigDecimal tasaCambio, String referencia) throws RollbackFailureException;

	public BigInteger crearTransferenciaBancaria(String numeroCuentaOrigen, String numeroCuentaDestino, BigDecimal monto, String referencia) throws RollbackFailureException;

	public void extornarTransaccion(BigInteger idTransaccion) throws RollbackFailureException;

	public BigInteger[] crearCuentaBancariaPlazoFijoConDeposito(String codigo, BigInteger idMoneda, TipoPersona tipoPersona, BigInteger idPersona, int cantRetirantes, BigDecimal monto, int periodo, BigDecimal tasaInteres, List<BigInteger> titulares, List<Beneficiario> beneficiarios) throws RollbackFailureException;

	public BigInteger cancelarCuentaBancariaConRetiro(BigInteger id) throws RollbackFailureException;

	public BigInteger cancelarSocioConRetiro(BigInteger idSocio) throws RollbackFailureException;

	public BigInteger crearPendiente(BigInteger idBoveda, BigDecimal monto, String observacion) throws RollbackFailureException;

	public BigInteger crearTransaccionBovedaCaja(BigInteger idBoveda, Set<GenericDetalle> detalleTransaccion) throws RollbackFailureException;

	public BigInteger crearTransaccionCajaCaja(BigInteger idCajadestino, BigInteger idMoneda, BigDecimal monto, String observacion) throws RollbackFailureException;

	public void cancelarTransaccionBovedaCaja(BigInteger idTransaccionBovedaCaja) throws RollbackFailureException;

	public void confirmarTransaccionBovedaCaja(BigInteger idTransaccionBovedaCaja) throws RollbackFailureException;

}

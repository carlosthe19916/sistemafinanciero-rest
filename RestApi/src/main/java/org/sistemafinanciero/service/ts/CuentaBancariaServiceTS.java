package org.sistemafinanciero.service.ts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Beneficiario;
import org.sistemafinanciero.entity.CuentaBancaria;
import org.sistemafinanciero.entity.Titular;
import org.sistemafinanciero.entity.type.TipoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoPersona;
import org.sistemafinanciero.exception.NonexistentEntityException;
import org.sistemafinanciero.exception.PreexistingEntityException;
import org.sistemafinanciero.exception.RollbackFailureException;

@Remote
public interface CuentaBancariaServiceTS extends AbstractServiceTS<CuentaBancaria> {

	public BigInteger create(TipoCuentaBancaria tipoCuentaBancaria, String codigoAgencia, BigInteger idMoneda, BigDecimal tasaInteres, TipoPersona tipoPersona, BigInteger idPersona, Integer periodo, int cantRetirantes, List<BigInteger> titulares, List<Beneficiario> beneficiarios) throws RollbackFailureException;

	public void congelarCuentaBancaria(BigInteger idCuentaBancaria) throws RollbackFailureException;

	public void descongelarCuentaBancaria(BigInteger idCuentaBancaria) throws RollbackFailureException;

	public BigInteger[] renovarCuentaPlazoFijo(BigInteger idCuenta, int periodo, BigDecimal tasaInteres) throws RollbackFailureException;

	public void recalcularCuentaPlazoFijo(BigInteger idCuenta, int periodo, BigDecimal tasaInteres) throws RollbackFailureException;

	public void capitalizarCuenta(BigInteger idCuentaBancaria) throws RollbackFailureException;

	public void cancelarCuentaBancaria(BigInteger id) throws RollbackFailureException;

	public BigInteger addBeneficiario(BigInteger id, Beneficiario beneficiario) throws RollbackFailureException;

	public void updateBeneficiario(BigInteger idBeneficiario, Beneficiario beneficiario) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException;

	public void deleteBeneficiario(BigInteger idBeneficiario) throws NonexistentEntityException, RollbackFailureException;

	public BigInteger addTitular(BigInteger idCuenta, Titular titular) throws RollbackFailureException;

	public void updateTitular(BigInteger idTitular, Titular titular) throws NonexistentEntityException, PreexistingEntityException, RollbackFailureException;

	public void deleteTitular(BigInteger idTitular) throws NonexistentEntityException, RollbackFailureException;

}

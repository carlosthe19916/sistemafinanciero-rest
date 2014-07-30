package org.sistemafinanciero.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.sistemafinanciero.dao.DAO;
import org.sistemafinanciero.dao.QueryParameter;
import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.TasaInteres;
import org.sistemafinanciero.entity.ValorTasaInteres;
import org.sistemafinanciero.entity.type.TasaInteresType;
import org.sistemafinanciero.service.nt.TasaInteresServiceNT;

@Named
@Stateless
@Remote(TasaInteresServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TasaInteresServiceBeanNT implements TasaInteresServiceNT {

	@Inject
	private DAO<Object, Moneda> monedaDAO;
	@Inject
	private DAO<Object, ValorTasaInteres> valorTasaInteresDAO;
	@Inject
	private DAO<Object, TasaInteres> tasaInteresDAO;

	@Override
	public BigDecimal getTasaInteresCuentaPlazoFijo(BigInteger idMoneda, int periodo, BigDecimal monto) {
		Moneda moneda = monedaDAO.find(idMoneda);
		ValorTasaInteres valorTasaInteres = null;
		if (moneda == null)
			return null;
		QueryParameter queryParameter = QueryParameter.with("tasaInteresDenominacion", TasaInteresType.TASA_CUENTA_PLAZO_FIJO.toString()).and("idMoneda", idMoneda).and("periodo", periodo).and("monto", monto);
		List<ValorTasaInteres> list = valorTasaInteresDAO.findByNamedQuery(ValorTasaInteres.finByDenominacionTasaAndIdMonedaPeriodoMonto, queryParameter.parameters());
		if (list.size() == 1)
			valorTasaInteres = list.get(0);

		if (valorTasaInteres != null)
			return valorTasaInteres.getValor();
		else
			return null;
	}

	@Override
	public BigDecimal getTasaInteresCuentaAhorro(BigInteger idMoneda) {
		Moneda moneda = monedaDAO.find(idMoneda);
		ValorTasaInteres valorTasaInteres = null;
		if (moneda == null)
			return null;
		QueryParameter queryParameter = QueryParameter.with("tasaInteresDenominacion", TasaInteresType.TASA_CUENTA_AHORRO.toString()).and("idMoneda", idMoneda);
		List<ValorTasaInteres> list = valorTasaInteresDAO.findByNamedQuery(ValorTasaInteres.finByDenominacionTasaAndIdMoneda, queryParameter.parameters());
		if (list.size() == 1)
			valorTasaInteres = list.get(0);

		if (valorTasaInteres != null)
			return valorTasaInteres.getValor();
		else
			return null;
	}

	@Override
	public BigDecimal getTasaInteresCuentaCorriente(BigInteger idMoneda) {
		Moneda moneda = monedaDAO.find(idMoneda);
		ValorTasaInteres valorTasaInteres = null;
		if (moneda == null)
			return null;
		QueryParameter queryParameter = QueryParameter.with("tasaInteresDenominacion", TasaInteresType.TASA_CUENTA_CORRIENTE.toString()).and("idMoneda", idMoneda);
		List<ValorTasaInteres> list = valorTasaInteresDAO.findByNamedQuery(ValorTasaInteres.finByDenominacionTasaAndIdMoneda, queryParameter.parameters());
		if (list.size() == 1)
			valorTasaInteres = list.get(0);

		if (valorTasaInteres != null)
			return valorTasaInteres.getValor();
		else
			return null;
	}

	@Override
	public BigDecimal getInteresGenerado(BigDecimal monto, int periodo, BigDecimal tasaInteres) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TasaInteresServiceNT findById(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TasaInteresServiceNT> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

}

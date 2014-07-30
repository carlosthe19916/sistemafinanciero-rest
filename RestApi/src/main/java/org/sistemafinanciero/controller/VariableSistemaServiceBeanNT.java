package org.sistemafinanciero.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
import org.sistemafinanciero.entity.VariableSistema;
import org.sistemafinanciero.entity.type.Variable;
import org.sistemafinanciero.service.nt.VariableSistemaServiceNT;
import org.sistemafinanciero.util.ProduceObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Stateless
@Remote(VariableSistemaServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VariableSistemaServiceBeanNT implements VariableSistemaServiceNT {

	private Logger LOGGER = LoggerFactory.getLogger(VariableSistemaServiceNT.class);

	@Inject
	private DAO<Object, VariableSistema> variableSistemaDAO;

	@Inject
	private DAO<Object, Moneda> monedaDAO;

	@Override
	public BigDecimal getTasaCambio(BigInteger idMonedaRecibida, BigInteger idMonedaEntregada) {
		Moneda monedaRecibida = monedaDAO.find(idMonedaRecibida);
		Moneda monedaEntregada = monedaDAO.find(idMonedaEntregada);
		if (monedaRecibida == null)
			return null;
		if (monedaEntregada == null)
			return null;

		String simboloMonedaRecibida = monedaRecibida.getSimbolo().toUpperCase();
		String simboloMonedaEntregada = monedaEntregada.getSimbolo().toUpperCase();

		BigDecimal result = null;

		switch (simboloMonedaRecibida) {
		case "S/.":
			if (simboloMonedaEntregada.equalsIgnoreCase("$")) {
				VariableSistema var = getVariable(Variable.TASA_VENTA_DOLAR);
				result = var.getValor();
			}
			if (simboloMonedaEntregada.equalsIgnoreCase("€")) {
				VariableSistema var = getVariable(Variable.TASA_VENTA_EURO);
				result = var.getValor();
			}
			break;
		case "$":
			if (simboloMonedaEntregada.equalsIgnoreCase("S/.")) {
				VariableSistema var = getVariable(Variable.TASA_COMPRA_DOLAR);
				result = var.getValor();
			}
			if (simboloMonedaEntregada.equalsIgnoreCase("€")) {
				VariableSistema var1 = getVariable(Variable.TASA_COMPRA_DOLAR);
				VariableSistema var2 = getVariable(Variable.TASA_VENTA_EURO);
				result = var2.getValor().divide(var1.getValor(), 3, RoundingMode.FLOOR);
			}
			break;
		case "€":
			if (simboloMonedaEntregada.equalsIgnoreCase("S/.")) {
				VariableSistema var = getVariable(Variable.TASA_COMPRA_EURO);
				result = var.getValor();
			}
			if (simboloMonedaEntregada.equalsIgnoreCase("$")) {
				VariableSistema var1 = getVariable(Variable.TASA_COMPRA_EURO);
				VariableSistema var2 = getVariable(Variable.TASA_VENTA_DOLAR);
				result = var2.getValor().divide(var1.getValor(), 3, RoundingMode.FLOOR);
			}
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public VariableSistema findById(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VariableSistema> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VariableSistema getVariable(Variable variable) {
		QueryParameter queryParameter = QueryParameter.with("denominacion", variable.toString());
		List<VariableSistema> list = variableSistemaDAO.findByNamedQuery(VariableSistema.findByDenominacion, queryParameter.parameters());
		for (VariableSistema variableSistema : list) {
			return variableSistema;
		}
		return null;
	}

}

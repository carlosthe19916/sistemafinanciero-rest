package org.sistemafinanciero.controller;

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
import org.sistemafinanciero.entity.Departamento;
import org.sistemafinanciero.entity.Distrito;
import org.sistemafinanciero.entity.Pais;
import org.sistemafinanciero.entity.Provincia;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.entity.type.TipoPersona;
import org.sistemafinanciero.service.nt.MaestroServiceNT;

@Named
@Stateless
@Remote(MaestroServiceNT.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MaestroServiceBeanNT implements MaestroServiceNT {

	@Inject
	private DAO<Object, TipoDocumento> tipodocumentoDAO;

	@Inject
	private DAO<Object, Pais> paisDAO;

	@Inject
	private DAO<Object, Departamento> departamentoDAO;
	@Inject
	private DAO<Object, Provincia> provinciaDAO;
	@Inject
	private DAO<Object, Distrito> distritoDAO;

	@Override
	public List<TipoDocumento> getTipoDocumento(TipoPersona tipopersona) {
		List<TipoDocumento> list = null;
		QueryParameter queryParameter = QueryParameter.with("tipopersona", tipopersona.toString());
		list = tipodocumentoDAO.findByNamedQuery(TipoDocumento.findByTipopersona, queryParameter.parameters());
		return list;
	}

	@Override
	public List<Pais> getPaises() {
		return paisDAO.findAll();
	}

	@Override
	public Pais findPaisByAbreviatura(String abrevitura) {
		if (abrevitura == null || abrevitura.isEmpty())
			return null;
		QueryParameter namedQueryName = QueryParameter.with("abreviatura", abrevitura);
		List<Pais> list = paisDAO.findByNamedQuery(Pais.findByAbreviatura, namedQueryName.parameters());
		if (list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	@Override
	public Pais findPaisByCodigo(String codigo) {
		if (codigo == null || codigo.isEmpty())
			return null;
		QueryParameter namedQueryName = QueryParameter.with("abreviatura", codigo);
		List<Pais> list = paisDAO.findByNamedQuery(Pais.findByCodigo, namedQueryName.parameters());
		if (list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	@Override
	public List<Departamento> getDepartamentos() {
		return departamentoDAO.findAll();
	}

	@Override
	public List<Provincia> getProvincias(BigInteger idDepartamento) {
		QueryParameter queryParameter = QueryParameter.with("iddepartamento", idDepartamento);
		List<Provincia> provincias = provinciaDAO.findByNamedQuery(Provincia.findByIdDepartamento, queryParameter.parameters());
		return provincias;
	}

	@Override
	public List<Distrito> getDistritos(BigInteger idProvincia) {
		QueryParameter queryParameter = QueryParameter.with("idprovincia", idProvincia);
		List<Distrito> provincias = distritoDAO.findByNamedQuery(Distrito.findByIdProvincia, queryParameter.parameters());
		return provincias;
	}

	@Override
	public List<Provincia> getProvincias(String codigoDepartamento) {
		QueryParameter queryParameter = QueryParameter.with("codigoDepartamento", codigoDepartamento);
		List<Provincia> provincias = provinciaDAO.findByNamedQuery(Provincia.findCodigoDepartamento, queryParameter.parameters());
		return provincias;
	}

	@Override
	public List<Distrito> getDistritos(String codigoDepartamento, String codigoProvincia) {
		QueryParameter queryParameter = QueryParameter.with("codigoDepartamento", codigoDepartamento).and("codigoProvincia", codigoProvincia);
		List<Distrito> distritos = distritoDAO.findByNamedQuery(Distrito.findCodigoProvincia, queryParameter.parameters());
		return distritos;
	}

}

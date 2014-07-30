package org.sistemafinanciero.rest.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.type.EstadoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoCuentaBancaria;
import org.sistemafinanciero.entity.type.TipoPersona;

@XmlRootElement(name = "buscarCuentaViewDTO")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class BuscarCuentaViewDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TipoPersona> tipoPersonaList;
	private List<TipoCuentaBancaria> tipoCuentaList;
	private List<EstadoCuentaBancaria> estadoCuentaList;
	private List<Moneda> monedaList;

	public List<TipoCuentaBancaria> getTipoCuentaList() {
		return tipoCuentaList;
	}

	public void setTipoCuentaList(List<TipoCuentaBancaria> tipoCuentaList) {
		this.tipoCuentaList = tipoCuentaList;
	}

	public List<EstadoCuentaBancaria> getEstadoCuentaList() {
		return estadoCuentaList;
	}

	public void setEstadoCuentaList(List<EstadoCuentaBancaria> estadoCuentaList) {
		this.estadoCuentaList = estadoCuentaList;
	}

	public List<TipoPersona> getTipoPersonaList() {
		return tipoPersonaList;
	}

	public void setTipoPersonaList(List<TipoPersona> tipoPersonaList) {
		this.tipoPersonaList = tipoPersonaList;
	}

	public List<Moneda> getMonedaList() {
		return monedaList;
	}

	public void setMonedaList(List<Moneda> monedaList) {
		this.monedaList = monedaList;
	}
}

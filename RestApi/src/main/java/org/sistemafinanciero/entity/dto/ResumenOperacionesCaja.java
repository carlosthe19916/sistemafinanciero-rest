package org.sistemafinanciero.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ResumenOperacionesCaja implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement
	private String agencia;

	@XmlElement
	private String caja;

	@XmlElement
	private Date fechaApertura;

	@XmlElement
	private Date fechaCierre;

	@XmlElement
	private Date horaApertura;

	@XmlElement
	private Date horaCierre;

	@XmlElement
	private String trabajador;
	
	@XmlElement
	private int depositosAporte;
	
	@XmlElement
	private int depositosPlazoFijo;
	
	@XmlElement
	private int depositosAhorro;
	
	@XmlElement
	private int depositosCorriente;
	
	@XmlElement
	private int retirosAporte;
	
	@XmlElement
	private int retirosPlazoFijo;
	
	@XmlElement
	private int retirosAhorro;
	
	@XmlElement
	private int retirosCorriente;
	
	@XmlElement
	private int compra;
	
	@XmlElement
	private int venta;
	
	@XmlElement
	private int depositoMayorCuantia;
	
	@XmlElement
	private int retiroMayorCuantia;
	
	@XmlElement
	private int compraVentaMayorCuantia;
	
	@XmlElement
	private int enviadoCajaCaja;
	
	@XmlElement
	private int recibidoCajaCaja;
	
	@XmlElement
	private int enviadoBovedaCaja;
	
	@XmlElement
	private int recibidoBovedaCaja;
	
	@XmlElement
	private int sobrante;
	
	@XmlElement
	private int faltante;
	
	@XmlElement
	private int pendienteSobrante;
	
	@XmlElement
	private int pendienteFaltante;

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getCaja() {
		return caja;
	}

	public void setCaja(String caja) {
		this.caja = caja;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Date getHoraApertura() {
		return horaApertura;
	}

	public void setHoraApertura(Date horaApertura) {
		this.horaApertura = horaApertura;
	}

	public Date getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
	}

	public String getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
	}

	public int getDepositosAporte() {
		return depositosAporte;
	}

	public void setDepositosAporte(int depositosAporte) {
		this.depositosAporte = depositosAporte;
	}

	public int getDepositosPlazoFijo() {
		return depositosPlazoFijo;
	}

	public void setDepositosPlazoFijo(int depositosPlazoFijo) {
		this.depositosPlazoFijo = depositosPlazoFijo;
	}

	public int getDepositosAhorro() {
		return depositosAhorro;
	}

	public void setDepositosAhorro(int depositosAhorro) {
		this.depositosAhorro = depositosAhorro;
	}

	public int getDepositosCorriente() {
		return depositosCorriente;
	}

	public void setDepositosCorriente(int depositosCorriente) {
		this.depositosCorriente = depositosCorriente;
	}

	public int getRetirosAporte() {
		return retirosAporte;
	}

	public void setRetirosAporte(int retirosAporte) {
		this.retirosAporte = retirosAporte;
	}

	public int getRetirosPlazoFijo() {
		return retirosPlazoFijo;
	}

	public void setRetirosPlazoFijo(int retirosPlazoFijo) {
		this.retirosPlazoFijo = retirosPlazoFijo;
	}

	public int getRetirosAhorro() {
		return retirosAhorro;
	}

	public void setRetirosAhorro(int retirosAhorro) {
		this.retirosAhorro = retirosAhorro;
	}

	public int getRetirosCorriente() {
		return retirosCorriente;
	}

	public void setRetirosCorriente(int retirosCorriente) {
		this.retirosCorriente = retirosCorriente;
	}

	public int getCompra() {
		return compra;
	}

	public void setCompra(int compra) {
		this.compra = compra;
	}

	public int getVenta() {
		return venta;
	}

	public void setVenta(int venta) {
		this.venta = venta;
	}

	public int getDepositoMayorCuantia() {
		return depositoMayorCuantia;
	}

	public void setDepositoMayorCuantia(int depositoMayorCuantia) {
		this.depositoMayorCuantia = depositoMayorCuantia;
	}

	public int getRetiroMayorCuantia() {
		return retiroMayorCuantia;
	}

	public void setRetiroMayorCuantia(int retiroMayorCuantia) {
		this.retiroMayorCuantia = retiroMayorCuantia;
	}

	public int getCompraVentaMayorCuantia() {
		return compraVentaMayorCuantia;
	}

	public void setCompraVentaMayorCuantia(int compraVentaMayorCuantia) {
		this.compraVentaMayorCuantia = compraVentaMayorCuantia;
	}

	public int getEnviadoCajaCaja() {
		return enviadoCajaCaja;
	}

	public void setEnviadoCajaCaja(int enviadoCajaCaja) {
		this.enviadoCajaCaja = enviadoCajaCaja;
	}

	public int getRecibidoCajaCaja() {
		return recibidoCajaCaja;
	}

	public void setRecibidoCajaCaja(int recibidoCajaCaja) {
		this.recibidoCajaCaja = recibidoCajaCaja;
	}

	public int getEnviadoBovedaCaja() {
		return enviadoBovedaCaja;
	}

	public void setEnviadoBovedaCaja(int enviadoBovedaCaja) {
		this.enviadoBovedaCaja = enviadoBovedaCaja;
	}

	public int getRecibidoBovedaCaja() {
		return recibidoBovedaCaja;
	}

	public void setRecibidoBovedaCaja(int recibidoBovedaCaja) {
		this.recibidoBovedaCaja = recibidoBovedaCaja;
	}

	public int getSobrante() {
		return sobrante;
	}

	public void setSobrante(int sobrante) {
		this.sobrante = sobrante;
	}

	public int getFaltante() {
		return faltante;
	}

	public void setFaltante(int faltante) {
		this.faltante = faltante;
	}

	public int getPendienteSobrante() {
		return pendienteSobrante;
	}

	public void setPendienteSobrante(int pendienteSobrante) {
		this.pendienteSobrante = pendienteSobrante;
	}

	public int getPendienteFaltante() {
		return pendienteFaltante;
	}

	public void setPendienteFaltante(int pendienteFaltante) {
		this.pendienteFaltante = pendienteFaltante;
	}

	
}

package org.sistemafinanciero.service.nt;

import java.math.BigInteger;
import java.util.Set;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.Boveda;
import org.sistemafinanciero.entity.Caja;
import org.sistemafinanciero.entity.dto.CajaCierreMoneda;
import org.sistemafinanciero.entity.dto.ResumenOperacionesCaja;
import org.sistemafinanciero.entity.dto.VoucherCompraVenta;
import org.sistemafinanciero.entity.dto.VoucherTransaccionBancaria;
import org.sistemafinanciero.entity.dto.VoucherTransaccionCuentaAporte;
import org.sistemafinanciero.entity.dto.VoucherTransferenciaBancaria;

@Remote
public interface CajaServiceNT extends AbstractServiceNT<Caja> {

	public Set<Boveda> getBovedas(BigInteger idCaja);

	public Set<CajaCierreMoneda> getVoucherCierreCaja(BigInteger idHistorial);

	public ResumenOperacionesCaja getResumenOperacionesCaja(BigInteger idHistorial);

	public VoucherTransaccionCuentaAporte getVoucherCuentaAporte(BigInteger idTransaccion);

	public VoucherTransaccionBancaria getVoucherTransaccionBancaria(BigInteger idTransaccionBancaria);

	public VoucherTransferenciaBancaria getVoucherTransferenciaBancaria(BigInteger idTransferencia);

	public VoucherCompraVenta getVoucherCompraVenta(BigInteger idTransaccionCompraVenta);

}

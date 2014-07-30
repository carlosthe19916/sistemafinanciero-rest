package org.sistemafinanciero.service.nt;

import java.math.BigInteger;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.PendienteCaja;
import org.sistemafinanciero.entity.dto.VoucherPendienteCaja;

@Remote
public interface PendienteServiceNT extends AbstractServiceNT<PendienteCaja> {

	public VoucherPendienteCaja getVoucherPendienteCaja(BigInteger idPendienteCaja);

}

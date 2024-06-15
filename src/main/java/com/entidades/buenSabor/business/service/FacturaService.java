package com.entidades.buenSabor.business.service;

import com.entidades.buenSabor.business.service.Base.BaseService;
import com.entidades.buenSabor.domain.entities.Factura;
import com.entidades.buenSabor.domain.entities.Pedido;

import java.io.IOException;

public interface FacturaService extends BaseService<Factura, Long> {
    byte[] generarFacturaPDF(Pedido pedido) throws IOException;
}

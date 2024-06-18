package com.entidades.buenSabor.business.service.Imp;

import com.entidades.buenSabor.domain.dto.Estadisticas.*;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.*;
import com.entidades.buenSabor.repositories.DetallePedidoRepository;
import com.entidades.buenSabor.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class EstadisticasDashboardServiceImp {

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    /* @Override
    public List<RankingProductosDto> productosMasVendidos(Date fechaDesde, Date fechaHasta) {
        return detallePedidoRepository.productosMasVendidos(fechaDesde, fechaHasta);
    }

    @Override
    public List<IngresosDiariosDto> ingresosDiarios(Date fechaDesde, Date fechaHasta) {
        return pedidoRepository.ingresosDiarios(fechaDesde, fechaHasta);
    }

    @Override
    public List<IngresosMensualesDto> ingresosMensuales(Date fechaDesde, Date fechaHasta) {
        return pedidoRepository.ingresosMensuales(fechaDesde, fechaHasta);
    }

    @Override
    public MontoGananciaDto findCostosGananciasByFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        return pedidoRepository.findCostosGananciasByFecha(fechaDesde, fechaHasta);
    }

    @Override
    public List<CantidadPedidosClienteDto> findCantidadPedidosPorCliente(LocalDate fechaDesde, LocalDate fechaHasta) {
        return pedidoRepository.findCantidadPedidosPorCliente(fechaDesde,fechaHasta);
    }
    */
}

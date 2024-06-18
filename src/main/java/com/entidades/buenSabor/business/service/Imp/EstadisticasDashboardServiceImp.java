package com.entidades.buenSabor.business.service.Imp;

import com.entidades.buenSabor.business.service.EstadisticasDashboardService;
import com.entidades.buenSabor.domain.dto.Estadisticas.*;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.*;

import com.entidades.buenSabor.repositories.DetallePedidoRepository;
import com.entidades.buenSabor.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class EstadisticasDashboardServiceImp implements EstadisticasDashboardService {

    @Autowired
    DetallePedidoRepository detallePedidoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public List<RankingProductosDto> productosMasVendidos(Date fechaDesde, Date fechaHasta) {
        return detallePedidoRepository.productosMasVendidos(fechaDesde, fechaHasta);
    }

    @Override
    public List<IngresosDiariosMensualesDto> ingresosDiarioYMensual(Date fechaDesde, Date fechaHasta) {
        return pedidoRepository.ingresosDiarioYMensual(fechaDesde, fechaHasta);
    }

    /* @Override
    public List<IngresosMensualesDto> ingresosMensuales(Date fechaDesde, Date fechaHasta) {
        return pedidoRepository.ingresosMensuales(fechaDesde, fechaHasta);
    }

    @Override
    public MontoGananciaDto findCostosGananciasByFecha(LocalDate fechaDesde, LocalDate fechaHasta) {
        return pedidoRepository.findCostosGananciasByFecha(fechaDesde, fechaHasta);
    } */

    @Override
    public List<CantidadPedidosClienteDto> findCantidadPedidosPorCliente(Date fechaDesde, Date fechaHasta) {
        return pedidoRepository.findCantidadPedidosPorCliente(fechaDesde,fechaHasta);
    }

}

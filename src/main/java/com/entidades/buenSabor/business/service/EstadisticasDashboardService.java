package com.entidades.buenSabor.business.service;

import com.entidades.buenSabor.domain.dto.Estadisticas.*;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EstadisticasDashboardService {

    List<RankingProductosDto> productosMasVendidos(Date fechaDesde, Date fechaHasta);
    List<IngresosDiariosDto> ingresosDiarios(Date fechaDesde, Date fechaHasta);
    List<IngresosMensualesDto> ingresosMensuales(Date fechaDesde, Date fechaHasta);
    MontoGananciaDto findCostosGananciasByFecha(LocalDate fechaDesde, LocalDate fechaHasta);
    List<CantidadPedidosClienteDto> findCantidadPedidosPorCliente(LocalDate fechaDesde, LocalDate fechaHasta);


}

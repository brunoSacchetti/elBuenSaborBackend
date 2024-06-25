package com.entidades.buenSabor.business.service;

import com.entidades.buenSabor.domain.dto.Estadisticas.*;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EstadisticasDashboardService {

    List<RankingProductosDto> productosMasVendidos(Date fechaDesde, Date fechaHasta);
    List<IngresosDiariosMensualesDto> ingresosDiarioYMensual(Date fechaDesde, Date fechaHasta);
    //List<IngresosMensualesDto> ingresosMensuales(Date fechaDesde, Date fechaHasta);
    MontoGananciaDto findCostosGananciasByFecha(Date fechaDesde, Date fechaHasta);
    List<CantidadPedidosClienteDto> findCantidadPedidosPorCliente(Date fechaDesde, Date fechaHasta);

    //Obtener fechas limites de los productos
    FechasLimites getFechasLimites();

    // METODOS EXCEL
    byte[] generarExcelRanking(Date fechaDesde, Date fechaHasta) throws IOException;

    byte[] generarExcelIngresos(Date fechaDesde, Date fechaHasta) throws IOException;

    byte[] generarExcelClientes(Date fechaDesde, Date fechaHasta) throws IOException;

    byte[] generarExcelResultadoEconomico(Date fechaDesde, Date fechaHasta) throws IOException;

}

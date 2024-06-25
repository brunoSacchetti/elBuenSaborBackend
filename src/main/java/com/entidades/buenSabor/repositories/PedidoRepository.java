package com.entidades.buenSabor.repositories;

import com.entidades.buenSabor.domain.dto.Estadisticas.CantidadPedidosClienteDto;
import com.entidades.buenSabor.domain.dto.Estadisticas.FechasLimites;
import com.entidades.buenSabor.domain.dto.Estadisticas.IngresosDiariosMensualesDto;
import com.entidades.buenSabor.domain.dto.Estadisticas.MontoGananciaDto;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.CostoGanancia;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.IngresosDiarios;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.IngresosMensuales;
import com.entidades.buenSabor.domain.dto.EstadisticasDashboard.PedidosCliente;
import com.entidades.buenSabor.domain.entities.Pedido;
import com.entidades.buenSabor.domain.enums.Estado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface PedidoRepository extends BaseRepository<Pedido,Long>{
    List<Pedido> findByEstado(Estado estado);

    @Query("SELECT NEW com.entidades.buenSabor.domain.dto.Estadisticas.IngresosDiariosMensualesDto(FUNCTION('DATE', p.fechaPedido), SUM(p.total)) " +
            "FROM Pedido p " +
            "WHERE p.eliminado = false " +
            "GROUP BY FUNCTION('DATE', p.fechaPedido) " +
            "ORDER BY FUNCTION('DATE', p.fechaPedido)")
    List<IngresosDiariosMensualesDto> ingresosDiarioYMensual(Date fechaDesde, Date fechaHasta);

    @Query("SELECT NEW com.entidades.buenSabor.domain.dto.Estadisticas.MontoGananciaDto(" +
            "SUM(dp.cantidad * ai.precioCompra), " +
            "SUM(p.total), " +
            "(SUM(p.total) - SUM(dp.cantidad * ai.precioCompra))) " +
            "FROM Pedido p " +
            "INNER JOIN p.detallePedidos dp " +
            "INNER JOIN dp.articulo a " +
            "INNER JOIN ArticuloInsumo ai ON a.id = ai.id " +
            "WHERE p.fechaPedido BETWEEN FUNCTION('DATE', :fechaDesde) AND FUNCTION('DATE', :fechaHasta) ")
    MontoGananciaDto findCostosGananciasByFecha(Date fechaDesde, Date fechaHasta);

    @Query("SELECT NEW com.entidades.buenSabor.domain.dto.Estadisticas.CantidadPedidosClienteDto(COUNT(p.id), c.nombre, c.apellido) " +
            "FROM Pedido p " +
            "INNER JOIN p.cliente c " +
            "WHERE p.eliminado = false " +
            "AND p.fechaPedido BETWEEN FUNCTION('DATE', :fechaDesde) AND FUNCTION('DATE', :fechaHasta) " +
            "GROUP BY c.id, c.nombre, c.apellido " +
            "ORDER BY COUNT(p.id) DESC")
    List<CantidadPedidosClienteDto> findCantidadPedidosPorCliente(Date fechaDesde, Date fechaHasta);


    @Query("SELECT NEW com.entidades.buenSabor.domain.dto.Estadisticas.FechasLimites(" +
            "function('DATE', MIN(p.fechaPedido)), function('DATE', MAX(p.fechaPedido))) " +
            "FROM Pedido p")
    FechasLimites getFechasLimites();

    List<Pedido> findByClienteId(Long clienteId);
}
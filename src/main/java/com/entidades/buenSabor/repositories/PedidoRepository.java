package com.entidades.buenSabor.repositories;

import com.entidades.buenSabor.domain.dto.Estadisticas.CantidadPedidosClienteDto;
import com.entidades.buenSabor.domain.dto.Estadisticas.IngresosDiariosMensualesDto;
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

    /* @Query(value = "SELECT FORMATDATETIME(p.fecha_pedido, 'yyyy-MM-dd') AS fecha, SUM(p.total) AS ingresos " +
            "FROM pedido p " +
            "WHERE p.fecha_pedido BETWEEN :initialDate AND :endDate " +
            "GROUP BY FORMATDATETIME(p.fecha_pedido, 'yyyy-MM-dd')", nativeQuery = true)
    List<IngresosDiariosDto> ingresosDiarios(Date fechaDesde, Date fechaHasta); */






    @Query("SELECT NEW com.entidades.buenSabor.domain.dto.Estadisticas.IngresosDiariosMensualesDto(FUNCTION('DATE', p.fechaPedido), SUM(p.total)) " +
            "FROM Pedido p " +
            "WHERE p.eliminado = false " +
            "GROUP BY FUNCTION('DATE', p.fechaPedido) " +
            "ORDER BY FUNCTION('DATE', p.fechaPedido)")
    List<IngresosDiariosMensualesDto> ingresosDiarioYMensual(Date fechaDesde, Date fechaHasta);


    @Query(value = "SELECT FORMATDATETIME(p.fecha_pedido, 'yyyy-MM') AS mes, SUM(p.total) AS ingresos " +
            "FROM Pedido p " +
            "WHERE p.fecha_pedido BETWEEN :startDate AND :endDate " +
            "GROUP BY FORMATDATETIME(p.fecha_pedido, 'yyyy-MM')", nativeQuery = true)
    List<IngresosMensuales> ingresosMensuales(Date startDate, Date endDate);

    @Query(value = "SELECT " +
            "SUM(dp.cantidad * ai.precioCompra) AS costos, " +
            "SUM(p.total) AS ganancias, " +
            "(SUM(p.total) - SUM(dp.cantidad * ai.precioCompra)) AS resultado " +
            "FROM Pedido p " +
            "JOIN p.detallePedidos dp " +
            "JOIN dp.articulo a " +
            "LEFT JOIN ArticuloInsumo ai ON a.id = ai.id " +
            "WHERE p.fechaPedido BETWEEN :initialDate AND :endDate")
    CostoGanancia findCostosGananciasByFecha(LocalDate initialDate, LocalDate endDate);

    /* @Query("SELECT p.cliente.email AS email, COUNT(p) AS cantidadPedidos " +
            "FROM Pedido p " +
            "WHERE p.fechaPedido BETWEEN :startDate AND :endDate " +
            "GROUP BY p.cliente.email " +
            "ORDER BY cantidadPedidos DESC")
    List<CantidadPedidosClienteDto> findCantidadPedidosPorCliente(LocalDate startDate, LocalDate endDate); */


    @Query("SELECT NEW com.entidades.buenSabor.domain.dto.Estadisticas.CantidadPedidosClienteDto(COUNT(p.id), c.nombre, c.apellido) " +
            "FROM Pedido p " +
            "INNER JOIN p.cliente c " +
            "WHERE p.eliminado = false " +
            "AND p.fechaPedido BETWEEN FUNCTION('DATE', :fechaDesde) AND FUNCTION('DATE', :fechaHasta) " +
            "GROUP BY c.id, c.nombre, c.apellido " +
            "ORDER BY COUNT(p.id) DESC")
    List<CantidadPedidosClienteDto> findCantidadPedidosPorCliente(Date fechaDesde, Date fechaHasta);



    List<Pedido> findByClienteId(Long clienteId);
}
package com.entidades.buenSabor.repositories;

import com.entidades.buenSabor.domain.dto.Estadisticas.RankingProductosDto;

import com.entidades.buenSabor.domain.entities.DetallePedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends BaseRepository<DetallePedido,Long>{
    @Query("SELECT NEW com.entidades.buenSabor.domain.dto.Estadisticas.RankingProductosDto(SUM(dp.cantidad), a.denominacion) " +
            "FROM Pedido p " +
            "INNER JOIN p.detallePedidos dp " +
            "INNER JOIN dp.articulo a " +
            "WHERE p.fechaPedido BETWEEN function('DATE', :fechaDesde) AND FUNCTION('DATE', :fechaHasta) " +
            "GROUP BY a.denominacion " +
            "ORDER BY SUM(dp.cantidad) DESC")
    List<RankingProductosDto> productosMasVendidos(Date fechaDesde, Date fechaHasta);

}

package com.entidades.buenSabor.repositories;

import com.entidades.buenSabor.domain.dto.Estadisticas.RankingProductosDto;

import com.entidades.buenSabor.domain.entities.DetallePedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends BaseRepository<DetallePedido,Long>{
    /* @Query(value = "select  am.denominacion as denominacion, count(am.id) as countVentas \n" +
            "from detalle_pedido dp\n" +
            "         inner join articulo am\n" +
            "                    on am.id = dp.articulo_id\n" +
            "         inner join pedido p\n" +
            "                    on dp.pedido_id = p.id\n" +
            "where PARSEDATETIME(p.fecha_pedido, 'yyyy-MM-dd') between :initialDate and :endDate \n" +
            "group by am.id,am.denominacion \n" +
            "order by countVentas desc;",
            nativeQuery = true) */

    @Query("SELECT NEW com.entidades.buenSabor.domain.dto.Estadisticas.RankingProductosDto(SUM(dp.cantidad), a.denominacion) " +
            "FROM Pedido p " +
            "INNER JOIN p.detallePedidos dp " +
            "INNER JOIN dp.articulo a " +
            "WHERE p.fechaPedido BETWEEN function('DATE', :fechaDesde) AND FUNCTION('DATE', :fechaHasta) " +
            "GROUP BY a.denominacion " +
            "ORDER BY SUM(dp.cantidad) DESC")
    List<RankingProductosDto> productosMasVendidos(Date fechaDesde, Date fechaHasta);

}

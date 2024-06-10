package com.entidades.buenSabor.domain.dto.DetallePedidoDto;

import com.entidades.buenSabor.domain.dto.Articulo.ArticuloDetallePedidoDto;
import com.entidades.buenSabor.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.entidades.buenSabor.domain.dto.BaseDto;
import com.entidades.buenSabor.domain.dto.Insumo.ArticuloInsumoDto;
import com.entidades.buenSabor.domain.entities.Articulo;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDto extends BaseDto {
    private Integer cantidad;
    private Double subTotal;
    private ArticuloInsumoDto insumo;
    private ArticuloManufacturadoDto manufacturado;
}

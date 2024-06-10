package com.entidades.buenSabor.domain.dto.DetallePedidoDto;

import com.entidades.buenSabor.domain.entities.Articulo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoPostDto {
    private Integer cantidad;
    private Double subTotal;
    private Long idArticulo;
}

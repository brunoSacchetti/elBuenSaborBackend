package com.entidades.buenSabor.domain.dto.Articulo;

import com.entidades.buenSabor.domain.entities.ImagenArticulo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloDetallePedidoDto {
    private String denominacion;
    private Double precioVenta;
    private Set<ImagenArticulo> imagenes = new HashSet<>();
}

package com.entidades.buenSabor.domain.dto.Estadisticas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RankingProductosDto {
    private Long cantidad;
    private String denominacion;
}

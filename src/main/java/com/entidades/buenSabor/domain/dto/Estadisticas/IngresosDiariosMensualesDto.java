package com.entidades.buenSabor.domain.dto.Estadisticas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngresosDiariosMensualesDto {
    private Date dia;
    private Double Ingresos;
}

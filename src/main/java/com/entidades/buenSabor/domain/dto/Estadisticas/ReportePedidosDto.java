package com.entidades.buenSabor.domain.dto.Estadisticas;

import com.entidades.buenSabor.domain.enums.Estado;
import com.entidades.buenSabor.domain.enums.FormaPago;
import com.entidades.buenSabor.domain.enums.TipoEnvio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportePedidosDto {
    private Long nroPedido;
    private Date fechaPedido;
    private int cantidad;
    private String denominacion; //denominacion del articulo
    private Double subtotal;
    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;
    private Estado estado;
    private String emailCliente; //email del cliente
}

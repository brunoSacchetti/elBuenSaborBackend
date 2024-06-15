package com.entidades.buenSabor.domain.dto.PedidoDto;

import com.entidades.buenSabor.domain.dto.DetallePedidoDto.DetallePedidoDto;
import com.entidades.buenSabor.domain.enums.FormaPago;
import com.entidades.buenSabor.domain.enums.TipoEnvio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPostDto {

    private TipoEnvio tipoEnvio;
    private FormaPago formaPago;

    private Set<DetallePedidoDto> detallePedidos;

    private Long idDomicilio;

    private Long idCliente;

}
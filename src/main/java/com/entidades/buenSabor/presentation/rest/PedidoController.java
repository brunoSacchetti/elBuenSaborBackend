package com.entidades.buenSabor.presentation.rest;

import com.entidades.buenSabor.business.facade.Imp.PedidoFacadeImp;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoCreateDto;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoDto;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoEditDto;
import com.entidades.buenSabor.domain.entities.Pedido;
import com.entidades.buenSabor.domain.enums.Estado;
import com.entidades.buenSabor.presentation.rest.Base.BaseControllerImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/pedido")
public class PedidoController extends BaseControllerImp<Pedido, PedidoDto, PedidoCreateDto, PedidoEditDto,Long, PedidoFacadeImp> {
    public PedidoController(PedidoFacadeImp facade) {
        super(facade);
    }

    @PutMapping("/CambiarEstadoPedido/{id}")
    public ResponseEntity<PedidoDto> cambiaEstado(@RequestBody Estado estado, @PathVariable Long id ) {
        return ResponseEntity.ok(facade.cambiaEstado(estado, id));
    }

}
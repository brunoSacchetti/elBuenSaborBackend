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
public class PedidoController extends BaseControllerImp<Pedido, PedidoDto, PedidoCreateDto, PedidoCreateDto,Long, PedidoFacadeImp> {
    public PedidoController(PedidoFacadeImp facade) {
        super(facade);
    }

    /* @PostMapping
    public ResponseEntity<?> create (@RequestBody PedidoCreateDto pedidoCreateDto){
        try {
            return super.create(pedidoCreateDto);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    } */

    @PostMapping
    public ResponseEntity<PedidoDto> create (@RequestBody PedidoCreateDto pedidoCreateDto){return super.create(pedidoCreateDto);}

    @PutMapping("/CambiarEstadoPedido/{id}")
    public ResponseEntity<PedidoDto> cambiaEstado(@RequestBody Estado estado, @PathVariable Long id ) {
        return ResponseEntity.ok(facade.cambiaEstado(estado, id));
    }

}
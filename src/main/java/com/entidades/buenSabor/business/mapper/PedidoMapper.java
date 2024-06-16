package com.entidades.buenSabor.business.mapper;

import com.entidades.buenSabor.business.service.ClienteService;
import com.entidades.buenSabor.business.service.DomicilioService;
import com.entidades.buenSabor.business.service.FacturaService;
import com.entidades.buenSabor.business.service.SucursalService;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoCreateDto;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoDto;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoEditDto;
import com.entidades.buenSabor.domain.entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DetallePedidoMapper.class, ClienteService.class, DomicilioService.class, FacturaService.class, FacturaMapper.class}) //SucursalService.class
public interface PedidoMapper extends BaseMapper<Pedido, PedidoDto, PedidoCreateDto, PedidoCreateDto> {


    //Por ahora no ocuparemos sucursal en ecommerce
    //@Mapping( source = "idSucursal", target = "sucursal", qualifiedByName = "getById")
    @Mapping(source = "domicilioID", target = "domicilio", qualifiedByName = "getById")
    @Mapping(source = "clienteID", target = "cliente", qualifiedByName = "getById")
    @Mapping(target = "detallePedidos", qualifiedByName = "toEntityCreateSetDetalle")
    Pedido toEntityCreate(PedidoCreateDto source);
}

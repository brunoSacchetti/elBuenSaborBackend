package com.entidades.buenSabor.business.mapper;

import com.entidades.buenSabor.business.service.PedidoService;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteCreateDto;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteDto;
import com.entidades.buenSabor.domain.dto.Promocion.PromocionDto;
import com.entidades.buenSabor.domain.entities.Cliente;
import com.entidades.buenSabor.domain.entities.Promocion;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { PedidoMapper.class, PedidoService.class, DomicilioMapper.class})
public interface ClienteMapper extends BaseMapper<Cliente, ClienteDto, ClienteCreateDto,ClienteCreateDto> {
    //source = de donde viene
    //target = a donde va

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    public ClienteDto toDTO(Cliente source);
    @Mappings({
//            //@Mapping(source = "idPedido", target = "pedidos",qualifiedByName = "getById"),
            @Mapping(source = "domicilios", target = "domicilios",qualifiedByName = "toEntityCreateSetDomicilio"),
            @Mapping(source = "imagenUrl", target = "imagenCliente.url"),
            @Mapping(source = "email",target = "imagenCliente.name"),
            //@Mapping(source = "usuario", target = "usuario")

    })
    Cliente toEntityCreate(ClienteCreateDto source);




}
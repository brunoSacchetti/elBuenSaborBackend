package com.entidades.buenSabor.business.mapper;

import com.entidades.buenSabor.business.service.ArticuloService;
import com.entidades.buenSabor.domain.dto.ArticuloManufacturado.ArticuloManufacturadoDto;
import com.entidades.buenSabor.domain.dto.DetallePedidoDto.DetallePedidoDto;
import com.entidades.buenSabor.domain.dto.DetallePedidoDto.DetallePedidoPostDto;
import com.entidades.buenSabor.domain.dto.Insumo.ArticuloInsumoDto;
import com.entidades.buenSabor.domain.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {ArticuloService.class, ArticuloInsumoMapper.class, ArticuloManufacturadoMapper.class})
public interface DetallePedidoMapper extends BaseMapper<DetallePedido, DetallePedidoDto, DetallePedidoPostDto, DetallePedidoPostDto>{

    // DetallePedidoMapper INSTANCE = Mappers.getMapper(DetallePedidoMapper.class);

    /* @Named("toEntityCreateSetDetalle") //Se asigno el metodo con la anotacion @Named de mapstruct para luego ser usado en ArticuloManufacturadoMapper
    @Mapping(target = "articulo", source = "idArticulo", qualifiedByName = "getById")
    Set<DetallePedido> toEntityCreateSetDetalle(Set<DetallePedidoPostDto> dtos); */

    /* @Named("toEntityCreateSetDetalle")
    @Mapping(target = "articulo", source = "idArticulo", qualifiedByName = "getById")
    Set<DetallePedido> toEntityCreateSetDetalle(Set<DetallePedidoPostDto> dtos); */


    // @Named("toDTO")
    // DetallePedidoDto toDTO(DetallePedido source);

    // Utiliza la anotación @Mapping para especificar el mapeo entre los campos del DTO y la entidad,
    // y utiliza el servicio ArticuloInsumoService para obtener el artículo de insumo a partir del ID.
    // @Mapping(target = "articulo", source = "idArticulo", qualifiedByName = "getById")
    // DetallePedido toEntityCreate(DetallePedidoPostDto source);

    // ---------------------------------------------------------
    DetallePedidoMapper INSTANCE = Mappers.getMapper(DetallePedidoMapper.class);

    //este lo usamos para el mapper de promocion
    @Named("toEntityCreateSetDetalle")
    Set<DetallePedido> toEntityCreateSetDetalle(Set<DetallePedidoPostDto> dtos);


    //usamos el parametro expression para indicar que vamos a usar un metodo para definir el mapeo
    @Mapping(target = "insumo", expression = "java(filterArticuloInsumo(source))")
    @Mapping(target = "manufacturado", expression = "java(filterArticuloManufacturado(source))")
    public DetallePedidoDto toDTO(DetallePedido source);

    @Mapping(target = "articulo", source = "idArticulo", qualifiedByName = "getById")
    public DetallePedido toEntityCreate(DetallePedidoPostDto source);

    default ArticuloInsumoDto filterArticuloInsumo(DetallePedido source) {
        return (source.getArticulo() instanceof ArticuloInsumo) ? ArticuloInsumoMapper.INSTANCE.toDTO((ArticuloInsumo) source.getArticulo()) : null;
    }

    default ArticuloManufacturadoDto filterArticuloManufacturado(DetallePedido source) {
        return (source.getArticulo() instanceof ArticuloManufacturado) ? ArticuloManufacturadoMapper.INSTANCE.toDTO((ArticuloManufacturado) source.getArticulo()) : null;
    }

}

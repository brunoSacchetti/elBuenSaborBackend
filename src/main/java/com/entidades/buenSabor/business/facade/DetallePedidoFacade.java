package com.entidades.buenSabor.business.facade;

import com.entidades.buenSabor.business.facade.Base.BaseFacade;
import com.entidades.buenSabor.domain.dto.ArticuloManufacturadoDetalle.ArticuloManufacturadoDetalleCreateDto;
import com.entidades.buenSabor.domain.dto.ArticuloManufacturadoDetalle.ArticuloManufacturadoDetalleDto;
import com.entidades.buenSabor.domain.dto.ArticuloManufacturadoDetalle.ArticuloManufacturadoDetalleEditDto;
import com.entidades.buenSabor.domain.dto.DetallePedidoDto.DetallePedidoDto;
import com.entidades.buenSabor.domain.dto.DetallePedidoDto.DetallePedidoPostDto;

public interface DetallePedidoFacade extends BaseFacade<DetallePedidoDto, DetallePedidoPostDto, DetallePedidoPostDto, Long> {
}

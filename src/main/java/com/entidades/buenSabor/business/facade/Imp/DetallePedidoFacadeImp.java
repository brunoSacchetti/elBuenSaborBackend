package com.entidades.buenSabor.business.facade.Imp;

import com.entidades.buenSabor.business.facade.ArticuloManufacturadoDetalleFacade;
import com.entidades.buenSabor.business.facade.Base.BaseFacadeImp;
import com.entidades.buenSabor.business.facade.DetallePedidoFacade;
import com.entidades.buenSabor.business.mapper.BaseMapper;
import com.entidades.buenSabor.business.service.Base.BaseService;
import com.entidades.buenSabor.domain.dto.ArticuloManufacturadoDetalle.ArticuloManufacturadoDetalleCreateDto;
import com.entidades.buenSabor.domain.dto.ArticuloManufacturadoDetalle.ArticuloManufacturadoDetalleDto;
import com.entidades.buenSabor.domain.dto.ArticuloManufacturadoDetalle.ArticuloManufacturadoDetalleEditDto;
import com.entidades.buenSabor.domain.dto.DetallePedidoDto.DetallePedidoDto;
import com.entidades.buenSabor.domain.dto.DetallePedidoDto.DetallePedidoPostDto;
import com.entidades.buenSabor.domain.entities.ArticuloManufacturadoDetalle;
import com.entidades.buenSabor.domain.entities.DetallePedido;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoFacadeImp extends BaseFacadeImp<DetallePedido, DetallePedidoDto, DetallePedidoPostDto, DetallePedidoPostDto, Long> implements DetallePedidoFacade {
    public DetallePedidoFacadeImp(BaseService<DetallePedido, Long> baseService, BaseMapper<DetallePedido, DetallePedidoDto, DetallePedidoPostDto, DetallePedidoPostDto> baseMapper) {
        super(baseService, baseMapper);
    }
}

package com.entidades.buenSabor.business.facade.Imp;

import com.entidades.buenSabor.business.facade.Base.BaseFacadeImp;
import com.entidades.buenSabor.business.facade.ClienteFacade;
import com.entidades.buenSabor.business.mapper.BaseMapper;
import com.entidades.buenSabor.business.mapper.ClienteMapper;
import com.entidades.buenSabor.business.mapper.DomicilioMapper;
import com.entidades.buenSabor.business.mapper.PedidoMapper;
import com.entidades.buenSabor.business.service.Base.BaseService;
import com.entidades.buenSabor.business.service.ClienteService;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteCreateDto;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteDto;
import com.entidades.buenSabor.domain.dto.Domicilio.DomicilioCreateDto;
import com.entidades.buenSabor.domain.dto.PedidoDto.PedidoDto;
import com.entidades.buenSabor.domain.entities.Cliente;
import com.entidades.buenSabor.domain.entities.Domicilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteFacadeImpl extends BaseFacadeImp<Cliente, ClienteDto, ClienteCreateDto,ClienteCreateDto,Long> implements ClienteFacade {
    public ClienteFacadeImpl(BaseService<Cliente, Long> baseService, BaseMapper<Cliente, ClienteDto, ClienteCreateDto, ClienteCreateDto> baseMapper) {
        super(baseService, baseMapper);
    }

    @Autowired
    public ClienteService clienteService;
    @Autowired
    public DomicilioMapper domicilioMapper;

    @Autowired
    public PedidoMapper pedidoMapper;

    @Override
    public ClienteDto añadirDomicilioCliente(DomicilioCreateDto domicilio, Long id){
        Domicilio domicilioCliente = domicilioMapper.toEntityCreate(domicilio);
        return baseMapper.toDTO(clienteService.añadirDomicilioCliente(domicilioCliente, id));
    }

    public List<PedidoDto> getAllPedidos(Long id) {
        return pedidoMapper.toDTOsList(clienteService.getAllPedido(id));
    }

}

package com.entidades.buenSabor.business.facade;

import com.entidades.buenSabor.business.facade.Base.BaseFacade;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteCreateDto;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteDto;
import com.entidades.buenSabor.domain.dto.Domicilio.DomicilioCreateDto;
import com.entidades.buenSabor.domain.dto.Domicilio.DomicilioDto;

import java.util.List;

public interface ClienteFacade extends BaseFacade<ClienteDto, ClienteCreateDto,ClienteCreateDto,Long> {
    ClienteDto añadirDomicilioCliente(DomicilioCreateDto domicilio, Long id);

    public Object getAllPedidos(Long id);

    public List<DomicilioDto> getAllDomicilios(Long id);
    ClienteDto addDomicilio(DomicilioCreateDto d, Long id);
}

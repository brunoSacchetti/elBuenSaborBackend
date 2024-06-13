package com.entidades.buenSabor.business.service;

import com.entidades.buenSabor.business.service.Base.BaseService;
import com.entidades.buenSabor.domain.dto.Domicilio.DomicilioCreateDto;
import com.entidades.buenSabor.domain.entities.Cliente;
import com.entidades.buenSabor.domain.entities.Domicilio;

public interface ClienteService extends BaseService<Cliente,Long> {
    Cliente findByEmail(String email);

    Cliente añadirDomicilioCliente(Domicilio domicilio, Long id);
}
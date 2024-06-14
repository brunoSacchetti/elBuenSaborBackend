package com.entidades.buenSabor.business.service;

import com.entidades.buenSabor.business.service.Base.BaseService;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteCreateDto;
import com.entidades.buenSabor.domain.dto.Domicilio.DomicilioCreateDto;
import com.entidades.buenSabor.domain.dto.LoginDto.LoginDto;
import com.entidades.buenSabor.domain.entities.Cliente;
import com.entidades.buenSabor.domain.entities.Domicilio;
import com.entidades.buenSabor.domain.entities.UsuarioEcommerce;

public interface ClienteService extends BaseService<Cliente,Long> {

    public Cliente crearClienteConUsuario(ClienteCreateDto clienteDto);

    public Cliente login(LoginDto loginDto);

    Cliente findByEmail(String email);

    Cliente añadirDomicilioCliente(Domicilio domicilio, Long id);


}
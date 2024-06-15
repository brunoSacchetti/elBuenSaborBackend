package com.entidades.buenSabor.business.service;

import com.entidades.buenSabor.business.service.Base.BaseService;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteCreateDto;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteDto;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteLoginDto;
import com.entidades.buenSabor.domain.dto.Domicilio.DomicilioCreateDto;
import com.entidades.buenSabor.domain.dto.LoginDto.LoginDto;
import com.entidades.buenSabor.domain.entities.Cliente;
import com.entidades.buenSabor.domain.entities.Domicilio;
import com.entidades.buenSabor.domain.entities.Pedido;
import com.entidades.buenSabor.domain.entities.UsuarioEcommerce;

import java.util.List;

public interface ClienteService extends BaseService<Cliente,Long> {

    public Cliente crearClienteConUsuario(ClienteCreateDto clienteDto);

    public ClienteDto login(LoginDto loginDto);

    Cliente findByEmail(String email);

    Cliente añadirDomicilioCliente(Domicilio domicilio, Long id);

    List<Pedido> getAllPedido(Long id);

    List<Domicilio>getAllDomicilios(Long id);

    Cliente addDomicilio(Domicilio d, Long id);


}
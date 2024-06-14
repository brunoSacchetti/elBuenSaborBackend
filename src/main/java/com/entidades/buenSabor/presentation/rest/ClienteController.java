package com.entidades.buenSabor.presentation.rest;

import com.entidades.buenSabor.business.facade.Imp.ClienteFacadeImpl;
import com.entidades.buenSabor.business.service.ClienteService;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteCreateDto;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteDto;
import com.entidades.buenSabor.domain.dto.LoginDto.LoginDto;
import com.entidades.buenSabor.domain.entities.Cliente;
import com.entidades.buenSabor.presentation.rest.Base.BaseControllerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@CrossOrigin("*")
public class ClienteController extends BaseControllerImp<Cliente, ClienteDto, ClienteCreateDto,ClienteCreateDto,Long, ClienteFacadeImpl> {
    public ClienteController(ClienteFacadeImpl facade) {
        super(facade);
    }

    @Autowired
    ClienteService clienteService;

    @PostMapping("/register-cliente")
    public ResponseEntity<Cliente> crearClienteConUsuario(@RequestBody ClienteCreateDto cliente) {
        Cliente clienteNew = clienteService.crearClienteConUsuario(cliente);
        return ResponseEntity.ok(clienteNew);
    }

    @PostMapping("/login")
    public ResponseEntity<Cliente> login(@RequestBody LoginDto loginDto) {
        Cliente cliente = clienteService.login(loginDto);
        return ResponseEntity.ok(cliente);
    }



}

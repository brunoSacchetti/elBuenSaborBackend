package com.entidades.buenSabor.business.service.Imp;

import com.entidades.buenSabor.business.mapper.ClienteMapper;
import com.entidades.buenSabor.business.service.Base.BaseServiceImp;
import com.entidades.buenSabor.business.service.ClienteService;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteCreateDto;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteDto;
import com.entidades.buenSabor.domain.dto.Cliente.ClienteLoginDto;
import com.entidades.buenSabor.domain.dto.Domicilio.DomicilioDto;
import com.entidades.buenSabor.domain.dto.Localidad.LocalidadDto;
import com.entidades.buenSabor.domain.dto.LoginDto.LoginDto;
import com.entidades.buenSabor.domain.entities.*;
import com.entidades.buenSabor.repositories.ClienteRepository;
import com.entidades.buenSabor.repositories.PedidoRepository;
import com.entidades.buenSabor.repositories.UsuarioEcommerceRepository;
import com.entidades.buenSabor.utils.PasswordEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl extends BaseServiceImp<Cliente,Long> implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    ClienteMapper clienteMapper;

    @Autowired
    UsuarioEcommerceRepository usuarioEcommerceRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public Cliente findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    public Cliente añadirDomicilioCliente(Domicilio domicilio, Long id){
        Cliente cliente = getById(id);
        cliente.getDomicilios().add(domicilio);


        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente crearClienteConUsuario(ClienteCreateDto clienteDto) {
        Cliente cliente = clienteMapper.toEntityCreate(clienteDto);

        // Encriptación de la clave
        cliente.setPassword(PasswordEncrypt.sha256(clienteDto.getPassword()));

        // Guarda el cliente
        return clienteRepository.save(cliente);
    }

    public ClienteDto login(LoginDto loginDto) {
        // Buscar el cliente por userName
        Cliente cliente = clienteRepository.findByUserName(loginDto.getUserName());
        if (cliente != null) {
            // Validar la contraseña
            String encryptedPassword = PasswordEncrypt.sha256(loginDto.getPassword());
            if (cliente.getPassword().equals(encryptedPassword)) {
                // Convertir Cliente a ClienteDto
                return clienteMapper.toDTO(cliente);
            }
        }
        throw new RuntimeException("Usuario o contraseña incorrectos");
    }

    @Override
    public List<Pedido> getAllPedido(Long id) {
        return pedidoRepository.findByClienteId(id);
    }

}

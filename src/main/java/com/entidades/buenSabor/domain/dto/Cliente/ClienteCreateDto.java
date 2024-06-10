package com.entidades.buenSabor.domain.dto.Cliente;

import com.entidades.buenSabor.domain.dto.Domicilio.DomicilioCreateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCreateDto {
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    private Set<DomicilioCreateDto> domicilios;

    private String imagenUrl;

}

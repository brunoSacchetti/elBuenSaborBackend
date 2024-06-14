package com.entidades.buenSabor.repositories;

import com.entidades.buenSabor.domain.entities.Usuario;
import com.entidades.buenSabor.domain.entities.UsuarioEcommerce;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioEcommerceRepository extends BaseRepository<UsuarioEcommerce,Long> {
}
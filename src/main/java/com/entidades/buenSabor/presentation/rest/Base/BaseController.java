package com.entidades.buenSabor.presentation.rest.Base;

import com.entidades.buenSabor.exceptions.RestrictDeleteException;
import com.entidades.buenSabor.domain.dto.BaseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;
import java.util.List;
//DC Dto create
//DE Dte edit
public interface BaseController <D extends BaseDto, DC, DE, ID extends Serializable> {
    ResponseEntity<D> getById(ID id);

    ResponseEntity<List<D>> getAll();

    ResponseEntity<D> create(DC entity);

    ResponseEntity<D> edit(DE entity, ID id);

    ResponseEntity<?> deleteById(ID id) throws RestrictDeleteException;
    ResponseEntity<?> changeEliminado(ID id);

    ResponseEntity<List<D>> getAllIncludingDeleted();
}
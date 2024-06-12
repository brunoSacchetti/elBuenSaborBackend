package com.entidades.buenSabor.presentation.rest;

import com.entidades.buenSabor.business.facade.Imp.ArticuloInsumoFacadeImp;
import com.entidades.buenSabor.business.service.ArticuloInsumoService;
import com.entidades.buenSabor.domain.dto.Insumo.ArticuloInsumoCreateDto;
import com.entidades.buenSabor.domain.dto.Insumo.ArticuloInsumoDto;
import com.entidades.buenSabor.domain.dto.Insumo.ArticuloInsumoEditDto;
import com.entidades.buenSabor.domain.entities.ArticuloInsumo;
import com.entidades.buenSabor.presentation.rest.Base.BaseControllerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/ArticuloInsumo")
public class ArticuloInsumoController  extends BaseControllerImp<ArticuloInsumo, ArticuloInsumoDto, ArticuloInsumoCreateDto, ArticuloInsumoEditDto, Long, ArticuloInsumoFacadeImp> {
    public ArticuloInsumoController(ArticuloInsumoFacadeImp facade) {
        super(facade);
    }

    @Autowired
    ArticuloInsumoService articuloInsumoService;

    @PostMapping
    @Override
    @PreAuthorize("hasAnyAuthority('EMPLEADO','ADMIN')")
    public ResponseEntity<ArticuloInsumoDto> create (ArticuloInsumoCreateDto entity){
        return super.create(entity);
    }

    @PutMapping("/{id}")
    @Override
    @PreAuthorize("hasAnyAuthority('EMPLEADO','ADMIN')")
    public ResponseEntity<ArticuloInsumoDto> edit(ArticuloInsumoEditDto editDto, Long id){
        return super.edit(editDto, id);
    }

    @PreAuthorize("hasAnyAuthority('EMPLEADO','ADMIN')")
    @PutMapping("/changeHabilitado/{id}")
    public ResponseEntity<?> changeHabilitado(@PathVariable Long id){
        facade.changeHabilitado(id);
        return ResponseEntity.ok().body("cambio de estado: Insumo");
    }

    @GetMapping("/getHabilitados")
    public ResponseEntity<?> getHabilitados(){
        return ResponseEntity.ok(facade.getAllHabilitados());
    }


    // Método POST para subir imágenes
    /* @PostMapping("/uploads")
    public ResponseEntity<String> uploadImages(
            @RequestParam(value = "uploads", required = true) MultipartFile[] files,
            @RequestParam(value = "id", required = true) Long idArticulo) {
        try {
            return facade.uploadImages(files, idArticulo); // Llama al método del servicio para subir imágenes
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo básico de errores, se puede mejorar para devolver una respuesta más específica
        }
    } */

    @PreAuthorize("hasAnyAuthority('EMPLEADO','ADMIN')")
    @PostMapping("/uploads")
    public ResponseEntity<String> uploadImages(@RequestParam("uploads") MultipartFile[] files,
                                               @RequestParam("id") Long idArticulo) {
        return articuloInsumoService.uploadImages(files, idArticulo);
    }

    //@PreAuthorize("hasAnyAuthority('EMPLEADO','ADMIN')")
    @DeleteMapping("/{publicId}/imagenes/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable String publicId, @PathVariable Long id) {
        return articuloInsumoService.deleteImage(publicId, id);
    }

    @GetMapping("/getImagesByArticuloId/{id}")
    public ResponseEntity<?> getAll(@PathVariable Long id) {
        try {
            return articuloInsumoService.getAllImagesByArticuloId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    // Método POST para eliminar imágenes por su publicId y Long
    /* @PostMapping("/deleteImg")
    public ResponseEntity<String> deleteById(
            @RequestParam(value = "publicId", required = true) String publicId,
            @RequestParam(value = "id", required = true) Long id) {
        try {
            return facade.deleteImage(publicId, id); // Llama al método del servicio para eliminar la imagen
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid UUID format"); // Respuesta HTTP 400 si el UUID no es válido
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred"); // Respuesta HTTP 500 si ocurre un error inesperado
        }
    } */

    // Método GET para obtener todas las imágenes almacenadas
    /* @GetMapping("/getImagesByArticuloId/{id}")
    public ResponseEntity<?> getAll(@PathVariable Long id) {
        try {
            return facade.getAllImagesByArticuloId(id); // Llama al método del servicio para obtener todas las imágenes
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo básico de errores, se puede mejorar para devolver una respuesta más específica
        }
    } */
}

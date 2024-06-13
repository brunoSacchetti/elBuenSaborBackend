package com.entidades.buenSabor.exceptions;
//Esta excepcion es una personalizada para el manejo de los delete cuando hay una eliminacion no valida
public class RestrictDeleteException extends RuntimeException {
    public RestrictDeleteException(String message) {
        super(message);
    }
}

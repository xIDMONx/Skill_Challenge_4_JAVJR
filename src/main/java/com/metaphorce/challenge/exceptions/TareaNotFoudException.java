package com.metaphorce.challenge.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Clase de excepción personalizada para TareaNotFoundException.
 *
 * Esta excepción se produce cuando no se encuentra una Tarea.
 */
public class TareaNotFoudException extends RuntimeException {

    private String codigo;

    /**
     * El estado HTTP de la respuesta.
     */
    private HttpStatus httpStatus;

    public TareaNotFoudException(String mensaje, String codigo, HttpStatus httpStatus) {
        super(mensaje);
        this.codigo = codigo;
        this.httpStatus = httpStatus;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return "TareaNotFoudException{" +
                "codigo='" + codigo + '\'' +
                ", httpStatus=" + httpStatus +
                '}';
    }
}

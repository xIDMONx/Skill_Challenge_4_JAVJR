package com.metaphorce.challenge.utils;

import java.util.List;
import java.util.ArrayList;

/**
 * La clase ErrorResponse representa un objeto de respuesta utilizado para encapsular información de error.
 */
public class ErrorResponse {

    private String codigo;
    private String mensaje;
    private List<String> errores;

    public ErrorResponse() {
    }

    public ErrorResponse(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ErrorResponse(String codigo, String mensaje, List<String> errores) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.errores = errores;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getErrores() {
        if (this.errores == null || this.errores.isEmpty()) {
            return new ArrayList<>();
        }

        return this.errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
}

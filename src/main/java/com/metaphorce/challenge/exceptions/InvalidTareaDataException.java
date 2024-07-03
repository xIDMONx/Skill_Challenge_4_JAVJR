package com.metaphorce.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

/**
 * Clase de excepción personalizada para datos de Tarea no válidos.
 */
public class InvalidTareaDataException extends RuntimeException {
    private String codigo;
    private HttpStatus httpStatus;

    /**
     * Representa el resultado de una operación de enlace de datos en Spring MVC.
     * <p>
     * La interfaz {@code BindingResult} amplía la interfaz {@code Errors} y proporciona funcionalidad adicional para
     * resolver errores de enlace y acceder a valores de campo y mensajes de error asociados.
     * <p>
     * Esta variable se utiliza en la clase {@code InvalidTareaDataException} para almacenar el resultado vinculante de datos de tareas no válidos.
     *
     * @see BindingResult
     * @see org.springframework.validation.Errors
     */
    private BindingResult bindingResult;

    public InvalidTareaDataException(String mensaje) {
        super(mensaje);
    }

    public InvalidTareaDataException(String mensaje, String codigo, HttpStatus httpStatus) {
        super(mensaje);
        this.codigo = codigo;
        this.httpStatus = httpStatus;
    }

    public InvalidTareaDataException(String mensaje, String codigo, HttpStatus httpStatus, BindingResult bindingResult) {
        super(mensaje);
        this.codigo = codigo;
        this.httpStatus = httpStatus;
        this.bindingResult = bindingResult;
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

    public BindingResult getBindingResult() {
        if (this.bindingResult == null) {
            return null;
        }
        return bindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    @Override
    public String toString() {
        return "InvalidTareaDataException{" +
                "codigo='" + codigo + '\'' +
                ", httpStatus=" + httpStatus +
                ", bindingResult=" + bindingResult +
                '}';
    }
}

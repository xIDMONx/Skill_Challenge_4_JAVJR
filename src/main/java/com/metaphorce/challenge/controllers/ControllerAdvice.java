package com.metaphorce.challenge.controllers;

import com.metaphorce.challenge.exceptions.InvalidTareaDataException;
import com.metaphorce.challenge.exceptions.TareaNotFoudException;
import com.metaphorce.challenge.utils.ErrorResponse;

import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InvalidTareaDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTareaDataException(InvalidTareaDataException e) {
        List<String> errores = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage).toList();

        ErrorResponse errorResponse = new ErrorResponse(e.getCodigo(), e.getMessage(), errores);
//        ErrorResponse errorResponse = new ErrorResponse(e.getCodigo(), e.getMessage());

        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }

    @ExceptionHandler(value = TareaNotFoudException.class)
    public ResponseEntity<ErrorResponse> handleTareaNotFoundException(TareaNotFoudException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodigo(e.getCodigo());
        errorResponse.setMensaje(e.getMessage());

        return new ResponseEntity<>(errorResponse, e.getHttpStatus());
    }
}

package com.barber.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HorarioIndisponivelException.class)
    public ResponseEntity erroHorario(HorarioIndisponivelException ex){
        return ResponseEntity.badRequest().body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity dataInvalida(DataInvalidaException ex){
        return ResponseEntity.badRequest().body(Map.of("erro", ex.getMessage()));
    }
}

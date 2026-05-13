package com.barber.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HorarioIndisponivelException.class)
    public ResponseEntity erroHorario(HorarioIndisponivelException ex){
        return ResponseEntity.badRequest().body(Map.of("erro", ex.getMessage()));

    }
}

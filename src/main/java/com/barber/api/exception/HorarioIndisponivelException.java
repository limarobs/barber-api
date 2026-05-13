package com.barber.api.exception;

public class HorarioIndisponivelException extends RuntimeException{
    public HorarioIndisponivelException(String message){
        super(message);
    }
}

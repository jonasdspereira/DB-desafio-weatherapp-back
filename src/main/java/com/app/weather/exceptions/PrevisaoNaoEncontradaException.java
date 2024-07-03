package com.app.weather.exceptions;

public class PrevisaoNaoEncontradaException extends RuntimeException{
    public PrevisaoNaoEncontradaException(String message) {
        super(message);
    }
}

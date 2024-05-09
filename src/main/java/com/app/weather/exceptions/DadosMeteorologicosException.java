package com.app.weather.exceptions;

public class DadosMeteorologicosException extends RuntimeException {

    public DadosMeteorologicosException(String message) {
        super(message);
    }

    public DadosMeteorologicosException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.app.weather.exceptions;

public class DadosMeteorologicosNaoInformadosException  extends Exception{
    private static final Long serialVersionUID = 2L;

    private String message;

    public DadosMeteorologicosNaoInformadosException(String message) {
        super(message);
    }
}

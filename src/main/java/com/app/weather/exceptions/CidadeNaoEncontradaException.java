package com.app.weather.exceptions;

import java.io.FileNotFoundException;

public class CidadeNaoEncontradaException extends Exception {
    private static final Long serialVersionUID = 1L;

    private String message;

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

}

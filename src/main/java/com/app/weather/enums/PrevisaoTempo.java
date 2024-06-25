package com.app.weather.enums;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PrevisaoTempo {
    PARCIALMENTE_NUBLADO("PARCIALMENTE NUBLADO"),
    ENSOLARADO("ENSOLARADO"),
    CHUVOSO("CHUVOSO"),
    VENTOSO("VENTOSO"),
    TEMPESTUOSO("TEMPESTUOSO"),
    NUBLADO("NUBLADO"),
    NEVADO("NEVADO"),
    LIMPO("LIMPO");

    private String value;

    PrevisaoTempo(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PrevisaoTempo fromValue(String value) {
        for (PrevisaoTempo previsao : PrevisaoTempo.values()) {
            if (previsao.value.equalsIgnoreCase(value)) {
                return previsao;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido: " + value);
    }
}


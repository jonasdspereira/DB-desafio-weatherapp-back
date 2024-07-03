package com.app.weather.dto;

import com.app.weather.enums.PrevisaoTempo;
import com.app.weather.enums.PrevisaoTurno;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PrevisaoDto(
        Long id,
        @NotEmpty(message = "O nome da cidade não pode estar vazio.")
        String nomeCidade,

        @NotNull(message = "A data da previsão não pode estar vazia.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataCadastro,

        @Enumerated(EnumType.STRING)
        PrevisaoTurno previsaoTurno,

        @Enumerated(EnumType.STRING)
        PrevisaoTempo previsaoTempo,

        @NotNull(message = "A temperatura não pode ser nula.")
        Integer temperaturaMaxima,

        @NotNull(message = "A temperatura não pode ser nula.")
        Integer temperaturaMinima,

        @NotNull(message = "A precipitação não pode ser nula.")
        Integer precipitacao,

        @NotNull(message = "A umidade não pode ser nula.")
        Integer umidade,

        @NotNull(message = "A velocidade do vento não pode ser nula.")
        Integer velocidadeDoVento
) {
}

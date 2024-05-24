package com.app.weather.dto;

import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CidadeDto {
    private Long id;

    @NotEmpty(message = "O nome da cidade não pode estar vazio.")
    private String nomeCidade;

    @NotNull(message = "A data da previsão não pode estar vazia.")
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    private CidadeTurno cidadeTurno;

    @Enumerated(EnumType.STRING)
    private CidadeTempo cidadeTempo;

    @NotNull(message = "A temperatura não pode ser nula.")
    private int temperaturaMaxima;

    @NotNull(message = "A temperatura não pode ser nula.")
    private int temperaturaMinima;

    @NotNull(message = "A precipitação não pode ser nula.")
    private int precipitacao;

    @NotNull(message = "A umidade não pode ser nula.")
    private int umidade;

    @NotNull(message = "A velocidade do vento não pode ser nulo.")
    private int velocidadeDoVento;
}

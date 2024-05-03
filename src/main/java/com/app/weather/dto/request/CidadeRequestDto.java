package com.app.weather.dto.request;

import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CidadeRequestDto {
    private Long id;
    private String nomeCidade;
    private LocalDateTime dataCadastro;
    private CidadeTurno cidadeTurno;
    private CidadeTempo cidadeTempo;
    private int temperaturaMaxima;
    private int temperaturaMinima;
    private int precipitacao;
    private int humidade;
    private int velocidadeDoVento;
}

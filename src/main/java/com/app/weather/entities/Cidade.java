package com.app.weather.entities;

import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Cidade {

    @GeneratedValue
    @Id
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    private String nomeCidade;
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    private CidadeTurno cidadeTurno;

    @Enumerated(EnumType.STRING)
    private CidadeTempo cidadeTempo;

    private int temperaturaMaxima;
    private int temperaturaMinima;
    private int precipitacao;
    private int humidade;
    private int velocidadeDoVento;
}

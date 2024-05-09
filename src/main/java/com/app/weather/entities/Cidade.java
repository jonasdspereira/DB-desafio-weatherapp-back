package com.app.weather.entities;

import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cidade {

    @GeneratedValue
    @Id
    private Long id;

    @NotEmpty(message = "Nome da cidade deve ser informado")
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

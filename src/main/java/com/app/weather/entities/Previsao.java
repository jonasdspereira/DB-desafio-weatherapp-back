package com.app.weather.entities;

import com.app.weather.enums.PrevisaoTempo;
import com.app.weather.enums.PrevisaoTurno;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Previsao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome da cidade não pode estar vazio.")
    @Size(min = 2, max = 30)
    private String nomeCidade;

    @NotNull(message = "A data da previsão não pode estar vazia.")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dataCadastro;

    @Enumerated(EnumType.STRING)
    private PrevisaoTurno previsaoTurno;

    @Enumerated(EnumType.STRING)
    private PrevisaoTempo previsaoTempo;

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

package com.app.weather.entities;

import com.app.weather.dto.PrevisaoDto;
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
public class Previsao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
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

    @NotNull(message = "A velocidade do vento não pode ser nula.")
    private int velocidadeDoVento;

    public Previsao(PrevisaoDto dto) {
        this.nomeCidade = dto.nomeCidade();
        this.dataCadastro = dto.dataCadastro();
        this.previsaoTurno = dto.previsaoTurno();
        this.previsaoTempo = dto.previsaoTempo();
        this.temperaturaMaxima = dto.temperaturaMaxima();
        this.temperaturaMinima = dto.temperaturaMinima();
        this.precipitacao = dto.precipitacao();
        this.umidade = dto.umidade();
        this.velocidadeDoVento = dto.velocidadeDoVento();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(@NotEmpty(message = "O nome da cidade não pode estar vazio.") @Size(min = 2, max = 30) String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(@NotNull(message = "A data da previsão não pode estar vazia.") LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public PrevisaoTurno getPrevisaoTurno() {
        return previsaoTurno;
    }

    public void setPrevisaoTurno(PrevisaoTurno previsaoTurno) {
        this.previsaoTurno = previsaoTurno;
    }

    public PrevisaoTempo getPrevisaoTempo() {
        return previsaoTempo;
    }

    public void setPrevisaoTempo(PrevisaoTempo previsaoTempo) {
        this.previsaoTempo = previsaoTempo;
    }

    @NotNull(message = "A temperatura não pode ser nula.")
    public int getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    public void setTemperaturaMaxima(@NotNull(message = "A temperatura não pode ser nula.") int temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    @NotNull(message = "A temperatura não pode ser nula.")
    public int getTemperaturaMinima() {
        return temperaturaMinima;
    }

    public void setTemperaturaMinima(@NotNull(message = "A temperatura não pode ser nula.") int temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    @NotNull(message = "A precipitação não pode ser nula.")
    public int getPrecipitacao() {
        return precipitacao;
    }

    public void setPrecipitacao(@NotNull(message = "A precipitação não pode ser nula.") int precipitacao) {
        this.precipitacao = precipitacao;
    }

    @NotNull(message = "A umidade não pode ser nula.")
    public int getUmidade() {
        return umidade;
    }

    public void setUmidade(@NotNull(message = "A umidade não pode ser nula.") int umidade) {
        this.umidade = umidade;
    }

    @NotNull(message = "A velocidade do vento não pode ser nula.")
    public int getVelocidadeDoVento() {
        return velocidadeDoVento;
    }

    public void setVelocidadeDoVento(@NotNull(message = "A velocidade do vento não pode ser nula.") int velocidadeDoVento) {
        this.velocidadeDoVento = velocidadeDoVento;
    }
}

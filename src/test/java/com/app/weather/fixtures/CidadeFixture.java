package com.app.weather.fixtures;

import com.app.weather.entities.Cidade;
import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;

import java.time.LocalDateTime;

public class CidadeFixture {

    public static Cidade gerarCidade() {
        LocalDateTime dataCadastro = LocalDateTime.of(2024,04,01,10,30,45);
        CidadeTempo tempo = CidadeTempo.LIMPO;
        CidadeTurno turno = CidadeTurno.MANHA;
        return new Cidade(1L, "Manaus",dataCadastro, turno, tempo, 30,29,70,65,120);
    }


}

package com.app.weather.repositories;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    @AfterEach
    void tearDown() {
        cidadeRepository.deleteAll();
    }

    @Test
    void testeCadastrarCidade() {
        LocalDateTime dataCadastro = LocalDateTime.of(2024,04,01,10,30,45);
        CidadeTempo tempo = CidadeTempo.LIMPO;
        CidadeTurno turno = CidadeTurno.MANHA;

        Cidade cidade = new Cidade(1L, "Manaus",dataCadastro, turno, tempo, 30,29,70,65,120);
        cidadeRepository.save(cidade);

        List<Cidade> cidades = cidadeRepository.findAll();

        assertEquals(1, cidades.size());
    }
}

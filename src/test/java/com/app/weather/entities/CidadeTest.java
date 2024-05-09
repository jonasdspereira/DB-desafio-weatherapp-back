package com.app.weather.entities;

import com.app.weather.entities.Cidade;
import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CidadeTest {

    @Test
    void testCanEqual() {
        // Given
        Cidade cidade1 = new Cidade();
        Cidade cidade2 = new Cidade();

        // Then
        assertTrue(cidade1.canEqual(cidade2));
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Cidade cidade = new Cidade();

        // When
        cidade.setId(1L);
        cidade.setNomeCidade("Manaus");
        LocalDateTime dataCadastro = LocalDateTime.now();
        cidade.setDataCadastro(dataCadastro);
        cidade.setCidadeTurno(CidadeTurno.MANHA);
        cidade.setCidadeTempo(CidadeTempo.LIMPO);
        cidade.setTemperaturaMaxima(30);
        cidade.setTemperaturaMinima(20);
        cidade.setPrecipitacao(10);
        cidade.setHumidade(80);
        cidade.setVelocidadeDoVento(10);

        // Then
        assertEquals(1L, cidade.getId());
        assertEquals("Manaus", cidade.getNomeCidade());
        assertEquals(dataCadastro, cidade.getDataCadastro());
        assertEquals(CidadeTurno.MANHA, cidade.getCidadeTurno());
        assertEquals(CidadeTempo.LIMPO, cidade.getCidadeTempo());
        assertEquals(30, cidade.getTemperaturaMaxima());
        assertEquals(20, cidade.getTemperaturaMinima());
        assertEquals(10, cidade.getPrecipitacao());
        assertEquals(80, cidade.getHumidade());
        assertEquals(10, cidade.getVelocidadeDoVento());
    }

    @Test
    void testToString() {
        // Given
        Cidade cidade = new Cidade(1L, "Manaus", LocalDateTime.now(), CidadeTurno.MANHA, CidadeTempo.LIMPO, 30, 20, 10, 80, 10);

        // Then
        assertEquals("Cidade(id=1, nomeCidade=Manaus, dataCadastro=" + cidade.getDataCadastro() + ", cidadeTurno=MANHA, cidadeTempo=LIMPO, temperaturaMaxima=30, temperaturaMinima=20, precipitacao=10, humidade=80, velocidadeDoVento=10)", cidade.toString());
    }

    @Test
    void testHashCode() {
        // Given
        Cidade cidade1 = new Cidade(1L, "Manaus", LocalDateTime.now(), CidadeTurno.MANHA, CidadeTempo.LIMPO, 30, 20, 10, 80, 10);
        Cidade cidade2 = new Cidade(1L, "Manaus", LocalDateTime.now(), CidadeTurno.MANHA, CidadeTempo.LIMPO, 30, 20, 10, 80, 10);

        // Then
        assertEquals(cidade1.hashCode(), cidade2.hashCode());
    }

    @Test
    void testEquals() {
        // Given
        Cidade cidade1 = new Cidade(1L, "Manaus", LocalDateTime.now(), CidadeTurno.MANHA, CidadeTempo.LIMPO, 30, 20, 10, 80, 10);
        Cidade cidade2 = new Cidade(1L, "Manaus", LocalDateTime.now(), CidadeTurno.MANHA, CidadeTempo.LIMPO, 30, 20, 10, 80, 10);
        Cidade cidade3 = new Cidade(2L, "São Paulo", LocalDateTime.now(), CidadeTurno.TARDE, CidadeTempo.CHUVOSO, 25, 15, 5, 75, 15);

        // Then
        assertTrue(cidade1.equals(cidade2));
        assertFalse(cidade1.equals(cidade3));
    }
}

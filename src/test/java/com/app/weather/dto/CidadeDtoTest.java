package com.app.weather.dto;

import com.app.weather.dto.CidadeDto;
import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CidadeDtoTest {

    @Test
    void testEqualsAndHashCode() {
        // Given
        LocalDateTime dataCadastro1 = LocalDateTime.of(2024, 5, 9, 0, 0);
        LocalDateTime dataCadastro2 = LocalDateTime.of(2024, 4, 9, 0, 0);
        CidadeDto cidadeDto1 = new CidadeDto(1L, "Manaus",dataCadastro1,CidadeTurno.MANHA, CidadeTempo.LIMPO,30,20,10,80,50);
        CidadeDto cidadeDto2 = new CidadeDto(1L, "Manaus",dataCadastro1,CidadeTurno.MANHA, CidadeTempo.LIMPO,30,20,10,80,50);
        CidadeDto cidadeDto3 = new CidadeDto(2L, "São Paulo",dataCadastro2,CidadeTurno.TARDE, CidadeTempo.CHUVOSO,20,20,10,80,50);

        // Then
        assertTrue(cidadeDto1.equals(cidadeDto2));
        assertEquals(cidadeDto1.hashCode(), cidadeDto2.hashCode());
    }

    @Test
    void testToString() {
        // Given
        LocalDateTime dataCadastro = LocalDateTime.of(2024, 5, 9, 0, 0);
        CidadeDto cidadeDto = new CidadeDto(1L, "Manaus", dataCadastro, CidadeTurno.MANHA, CidadeTempo.LIMPO, 30, 20, 10, 80, 10);

        // Then
        assertEquals("CidadeDto(id=1, nomeCidade=Manaus, dataCadastro=2024-05-09T00:00, cidadeTurno=MANHA, cidadeTempo=LIMPO, temperaturaMaxima=30, temperaturaMinima=20, precipitacao=10, humidade=80, velocidadeDoVento=10)", cidadeDto.toString());
    }
}

package com.app.weather.mapper;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.entities.Previsao;
import com.app.weather.enums.PrevisaoTempo;
import com.app.weather.enums.PrevisaoTurno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class PrevisaoMapperTest {

    private PrevisaoMapper previsaoMapper;

    private Previsao previsao;
    private PrevisaoDto previsaoDto;

    @BeforeEach
    void setup() {
        previsaoMapper = Mappers.getMapper(PrevisaoMapper.class);

        previsao = Previsao.builder()
                .id(1L)
                .nomeCidade("Manaus")
                .dataCadastro(LocalDate.now())
                .previsaoTurno(PrevisaoTurno.NOITE)
                .previsaoTempo(PrevisaoTempo.LIMPO)
                .temperaturaMaxima(30)
                .temperaturaMinima(20)
                .precipitacao(5)
                .umidade(60)
                .velocidadeDoVento(120)
                .build();

        previsaoDto = PrevisaoDto.criarPrevisao("Manaus", LocalDate.now(), PrevisaoTurno.NOITE, PrevisaoTempo.LIMPO, 30, 25, 50, 80, 120);

    }

    @Test
    @DisplayName("Deve converter entidade para DTO corretamente")
    void deveConverterEntidadeParaDtoCorretamente() {
        PrevisaoDto dto = previsaoMapper.toDto(previsao);

        assertNotNull(dto);
        assertEquals(previsao.getNomeCidade(), dto.nomeCidade());
        assertEquals(previsao.getDataCadastro(), dto.dataCadastro());
        assertEquals(previsao.getPrevisaoTurno(), dto.previsaoTurno());
        assertEquals(previsao.getPrevisaoTempo(), dto.previsaoTempo());
        assertEquals(previsao.getTemperaturaMaxima(), dto.temperaturaMaxima());
        assertEquals(previsao.getTemperaturaMinima(), dto.temperaturaMinima());
        assertEquals(previsao.getPrecipitacao(), dto.precipitacao());
        assertEquals(previsao.getUmidade(), dto.umidade());
        assertEquals(previsao.getVelocidadeDoVento(), dto.velocidadeDoVento());
    }

    @Test
    @DisplayName("Deve converter DTO para entidade corretamente")
    void deveConverterDtoParaEntidadeCorretamente() {
        Previsao entity = previsaoMapper.toEntity(previsaoDto);

        assertNotNull(entity);
        assertEquals(previsaoDto.nomeCidade(), entity.getNomeCidade());
        assertEquals(previsaoDto.dataCadastro(), entity.getDataCadastro());
        assertEquals(previsaoDto.previsaoTurno(), entity.getPrevisaoTurno());
        assertEquals(previsaoDto.previsaoTempo(), entity.getPrevisaoTempo());
        assertEquals(previsaoDto.temperaturaMaxima(), entity.getTemperaturaMaxima());
        assertEquals(previsaoDto.temperaturaMinima(), entity.getTemperaturaMinima());
        assertEquals(previsaoDto.precipitacao(), entity.getPrecipitacao());
        assertEquals(previsaoDto.umidade(), entity.getUmidade());
        assertEquals(previsaoDto.velocidadeDoVento(), entity.getVelocidadeDoVento());
    }
}

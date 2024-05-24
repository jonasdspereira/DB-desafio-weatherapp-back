package com.app.weather.mapper;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CidadeMapperTest {

    private CidadeMapper cidadeMapper;

    private Cidade cidade;
    private CidadeDto cidadeDto;

    @BeforeEach
    void setup() {
        cidadeMapper = Mappers.getMapper(CidadeMapper.class);

        cidade = Cidade.builder()
                .id(1L)
                .nomeCidade("Manaus")
                .dataCadastro(LocalDate.now())
                .cidadeTurno(CidadeTurno.NOITE)
                .cidadeTempo(CidadeTempo.LIMPO)
                .temperaturaMaxima(30)
                .temperaturaMinima(20)
                .precipitacao(5)
                .umidade(60)
                .velocidadeDoVento(120)
                .build();

        cidadeDto = CidadeDto.builder()
                .id(1L)
                .nomeCidade("Manaus")
                .dataCadastro(LocalDate.now())
                .cidadeTurno(CidadeTurno.NOITE)
                .cidadeTempo(CidadeTempo.LIMPO)
                .temperaturaMaxima(30)
                .temperaturaMinima(20)
                .precipitacao(5)
                .umidade(60)
                .velocidadeDoVento(120)
                .build();
    }

    @Test
    @DisplayName("Deve converter entidade para DTO corretamente")
    void deveConverterEntidadeParaDtoCorretamente() {
        CidadeDto dto = cidadeMapper.toDto(cidade);

        assertNotNull(dto);
        assertEquals(cidade.getId(), dto.getId());
        assertEquals(cidade.getNomeCidade(), dto.getNomeCidade());
        assertEquals(cidade.getDataCadastro(), dto.getDataCadastro());
        assertEquals(cidade.getCidadeTurno(), dto.getCidadeTurno());
        assertEquals(cidade.getCidadeTempo(), dto.getCidadeTempo());
        assertEquals(cidade.getTemperaturaMaxima(), dto.getTemperaturaMaxima());
        assertEquals(cidade.getTemperaturaMinima(), dto.getTemperaturaMinima());
        assertEquals(cidade.getPrecipitacao(), dto.getPrecipitacao());
        assertEquals(cidade.getUmidade(), dto.getUmidade());
        assertEquals(cidade.getVelocidadeDoVento(), dto.getVelocidadeDoVento());
    }

    @Test
    @DisplayName("Deve converter DTO para entidade corretamente")
    void deveConverterDtoParaEntidadeCorretamente() {
        Cidade entity = cidadeMapper.toEntity(cidadeDto);

        assertNotNull(entity);
        assertEquals(cidadeDto.getId(), entity.getId());
        assertEquals(cidadeDto.getNomeCidade(), entity.getNomeCidade());
        assertEquals(cidadeDto.getDataCadastro(), entity.getDataCadastro());
        assertEquals(cidadeDto.getCidadeTurno(), entity.getCidadeTurno());
        assertEquals(cidadeDto.getCidadeTempo(), entity.getCidadeTempo());
        assertEquals(cidadeDto.getTemperaturaMaxima(), entity.getTemperaturaMaxima());
        assertEquals(cidadeDto.getTemperaturaMinima(), entity.getTemperaturaMinima());
        assertEquals(cidadeDto.getPrecipitacao(), entity.getPrecipitacao());
        assertEquals(cidadeDto.getUmidade(), entity.getUmidade());
        assertEquals(cidadeDto.getVelocidadeDoVento(), entity.getVelocidadeDoVento());
    }
}

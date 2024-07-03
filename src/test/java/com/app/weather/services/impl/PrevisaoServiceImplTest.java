package com.app.weather.services.impl;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.entities.Previsao;
import com.app.weather.exceptions.CamposDosDadosMeteorologicosNaoInformadosException;
import com.app.weather.exceptions.PrevisaoNaoEncontradaException;
import com.app.weather.mapper.PrevisaoMapper;
import com.app.weather.repositories.PrevisaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.app.weather.enums.PrevisaoTempo.CHUVOSO;
import static com.app.weather.enums.PrevisaoTurno.NOITE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrevisaoServiceImplTest {

    @InjectMocks
    private PrevisaoServiceImpl previsaoService;

    @Mock
    private PrevisaoRepository previsaoRepository;

    @Mock
    private PrevisaoMapper previsaoMapper;

    private Previsao previsao;
    private PrevisaoDto previsaoDto;

    @BeforeEach
    void setup() {
        previsao = new Previsao();
        previsao.setId(1L);
        previsao.setNomeCidade("Manaus");
        previsao.setDataCadastro(LocalDate.of(2024, 7, 2));
        previsao.setPrevisaoTurno(NOITE);
        previsao.setPrevisaoTempo(CHUVOSO);
        previsao.setTemperaturaMaxima(25);
        previsao.setTemperaturaMinima(15);
        previsao.setPrecipitacao(20);
        previsao.setUmidade(50);
        previsao.setVelocidadeDoVento(10);

        previsaoDto = new PrevisaoDto(1L, "Manaus", LocalDate.of(2024, 7, 2), NOITE, CHUVOSO, 25, 15, 20, 50, 10);
    }

    @Test
    void salvarDadosMeteorologicos() {

        when(previsaoMapper.toEntity(any(PrevisaoDto.class))).thenReturn(previsao);
        when(previsaoRepository.save(any(Previsao.class))).thenReturn(previsao);
        when(previsaoMapper.toDto(any(Previsao.class))).thenReturn(previsaoDto);


        PrevisaoDto resultado = previsaoService.salvarDadosMeteorologicos(previsaoDto);


        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(previsaoDto.nomeCidade(), resultado.nomeCidade(), "O nome da cidade deve ser igual ao esperado");
        assertEquals(previsaoDto.previsaoTempo(), resultado.previsaoTempo(), "A previsão do tempo deve ser igual ao esperado");
        assertEquals(previsaoDto.previsaoTurno(), resultado.previsaoTurno(), "O turno da previsão deve ser igual ao esperado");
        assertEquals(previsaoDto.temperaturaMaxima(), resultado.temperaturaMaxima(), "A temperatura máxima deve ser igual ao esperado");
        assertEquals(previsaoDto.temperaturaMinima(), resultado.temperaturaMinima(), "A temperatura mínima deve ser igual ao esperado");
        assertEquals(previsaoDto.precipitacao(), resultado.precipitacao(), "A precipitação deve ser igual ao esperado");
        assertEquals(previsaoDto.umidade(), resultado.umidade(), "A umidade deve ser igual ao esperado");
        assertEquals(previsaoDto.velocidadeDoVento(), resultado.velocidadeDoVento(), "A velocidade do vento deve ser igual ao esperado");


        verify(previsaoRepository, times(1)).save(any(Previsao.class));
        verify(previsaoMapper, times(1)).toEntity(any(PrevisaoDto.class));
        verify(previsaoMapper, times(1)).toDto(any(Previsao.class));


        ArgumentCaptor<Previsao> previsaoCaptor = ArgumentCaptor.forClass(Previsao.class);
        verify(previsaoRepository).save(previsaoCaptor.capture());
        Previsao previsaoSalva = previsaoCaptor.getValue();

        assertEquals(previsaoDto.nomeCidade(), previsaoSalva.getNomeCidade(), "O nome da cidade deve ser igual ao esperado");
        assertEquals(previsaoDto.previsaoTempo(), previsaoSalva.getPrevisaoTempo(), "A previsão do tempo deve ser igual ao esperado");
        assertEquals(previsaoDto.previsaoTurno(), previsaoSalva.getPrevisaoTurno(), "O turno da previsão deve ser igual ao esperado");
        assertEquals(previsaoDto.temperaturaMaxima(), previsaoSalva.getTemperaturaMaxima(), "A temperatura máxima deve ser igual ao esperado");
        assertEquals(previsaoDto.temperaturaMinima(), previsaoSalva.getTemperaturaMinima(), "A temperatura mínima deve ser igual ao esperado");
        assertEquals(previsaoDto.precipitacao(), previsaoSalva.getPrecipitacao(), "A precipitação deve ser igual ao esperado");
        assertEquals(previsaoDto.umidade(), previsaoSalva.getUmidade(), "A umidade deve ser igual ao esperado");
        assertEquals(previsaoDto.velocidadeDoVento(), previsaoSalva.getVelocidadeDoVento(), "A velocidade do vento deve ser igual ao esperado");
    }


    @Test
    void salvarDadosMeteorologicos_nomeCidadeVazio() {
        PrevisaoDto previsaoDtoComNomeVazio = new PrevisaoDto(1L, "", LocalDate.of(2024, 7, 2), NOITE, CHUVOSO, 25, 15, 20, 1, 1);

        assertThrows(CamposDosDadosMeteorologicosNaoInformadosException.class, () -> {
            previsaoService.salvarDadosMeteorologicos(previsaoDtoComNomeVazio);
        });

        verify(previsaoRepository, never()).save(any(Previsao.class));
    }

    @Test
    void buscarPrevisoes() {

        when(previsaoRepository.findAll()).thenReturn(Collections.singletonList(previsao));
        when(previsaoMapper.toDto(any(Previsao.class))).thenReturn(previsaoDto);


        List<PrevisaoDto> resultado = previsaoService.buscarPrevisoes();


        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertFalse(resultado.isEmpty(), "O resultado não deve estar vazio");
        assertEquals(previsaoDto.nomeCidade(), resultado.get(0).nomeCidade(), "O nome da cidade deve corresponder");


        verify(previsaoRepository, times(1)).findAll();
        verify(previsaoMapper, times(1)).toDto(any(Previsao.class));
    }

    @Test
    void buscarPrevisao() {

        when(previsaoRepository.findById(anyLong())).thenReturn(Optional.of(previsao));
        when(previsaoMapper.toDto(any(Previsao.class))).thenReturn(previsaoDto);


        PrevisaoDto resultado = previsaoService.buscarPrevisao(1L);


        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(previsaoDto.nomeCidade(), resultado.nomeCidade(), "O nome da cidade deve corresponder");


        verify(previsaoRepository, times(1)).findById(anyLong());
        verify(previsaoMapper, times(1)).toDto(any(Previsao.class));
    }

    @Test
    void buscarPrevisao_naoEncontrada() {
        when(previsaoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PrevisaoNaoEncontradaException.class, () -> {
            previsaoService.buscarPrevisao(1L);
        });

        verify(previsaoRepository, times(1)).findById(anyLong());
        verify(previsaoMapper, never()).toDto(any(Previsao.class));
    }

    @Test
    void buscarPrevisaoPeloNome() {
        when(previsaoRepository.findByNomeCidade(anyString())).thenReturn(Collections.singletonList(previsao));
        when(previsaoMapper.toDto(any(Previsao.class))).thenReturn(previsaoDto);

        List<PrevisaoDto> resultado = previsaoService.buscarPrevisaoPeloNome("Cidade Teste");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(previsaoDto.nomeCidade(), resultado.get(0).nomeCidade());

        verify(previsaoRepository, times(1)).findByNomeCidade(anyString());
        verify(previsaoMapper, times(1)).toDto(any(Previsao.class));
    }

    @Test
    void alterarDadosMeteorologicos() {
        when(previsaoRepository.findById(anyLong())).thenReturn(Optional.of(previsao));
        when(previsaoRepository.save(any(Previsao.class))).thenReturn(previsao);
        when(previsaoMapper.toDto(any(Previsao.class))).thenReturn(previsaoDto);

        PrevisaoDto resultado = previsaoService.alterarDadosMeteorologicos(1L, previsaoDto);

        assertNotNull(resultado);
        assertEquals(previsaoDto.nomeCidade(), resultado.nomeCidade());

        verify(previsaoRepository, times(1)).findById(anyLong());
        verify(previsaoRepository, times(1)).save(any(Previsao.class));
        verify(previsaoMapper, times(1)).toDto(any(Previsao.class));
    }

    @Test
    void excluirDadosMeteorologicos() {
        previsaoService.excluirDadosMeteorologicos(1L);

        verify(previsaoRepository, times(1)).deleteById(anyLong());
    }
}

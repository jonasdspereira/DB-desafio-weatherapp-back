package com.app.weather.services.impl;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.entities.Previsao;
import com.app.weather.enums.PrevisaoTempo;
import com.app.weather.enums.PrevisaoTurno;
import com.app.weather.exceptions.CamposDosDadosMeteorologicosNaoInformadosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.mapper.PrevisaoMapper;
import com.app.weather.repositories.PrevisaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
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
        MockitoAnnotations.openMocks(this);

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
        previsaoService = new PrevisaoServiceImpl(previsaoRepository, previsaoMapper);
    }

    @Test
    @DisplayName("Deve salvar dados meteorológicos corretamente")
    void deveSalvarDadosMeteorologicosCorretamente() {

        when(previsaoMapper.toEntity(any(PrevisaoDto.class))).thenReturn(previsao);
        when(previsaoRepository.save(any(Previsao.class))).thenReturn(previsao);
        when(previsaoMapper.toDto(any(Previsao.class))).thenReturn(previsaoDto);

        PrevisaoDto resultado = previsaoService.salvarDadosMeteorologicos(previsaoDto);

        assertNotNull(resultado);
        assertEquals("Manaus", resultado.nomeCidade());

        verify(previsaoRepository, times(1)).save(any(Previsao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome da cidade não estiver informado")
    void deveLancarExcecaoQuandoNomeCidadeNaoInformado() {
        PrevisaoDto previsaoDto = new PrevisaoDto("", LocalDate.now(), PrevisaoTurno.NOITE, PrevisaoTempo.LIMPO, 20,15,50,80,120 );

        CamposDosDadosMeteorologicosNaoInformadosException exception = assertThrows(
                CamposDosDadosMeteorologicosNaoInformadosException.class,
                () -> previsaoService.salvarDadosMeteorologicos(previsaoDto)
        );

        assertEquals("O nome da cidade não pode estar vazio.", exception.getMessage());
        verify(previsaoRepository, never()).save(any(Previsao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar previsão atual")
    void deveLancarExcecaoQuandoNaoEncontrarPrevisaoAtual() {
        when(previsaoRepository.findByDataCadastroAndNomeCidade(any(LocalDate.class), anyString())).thenReturn(Optional.empty());

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            previsaoService.buscarPrevisaoAtual("Manaus");
        });

        assertEquals("Nenhuma previsão encontrada para a cidade: Manaus", exception.getMessage());
    }

    @Test
    @DisplayName("Deve buscar previsão para os próximos 7 dias com sucesso")
    void deveBuscarPrevisao7DiasComSucesso() {
        List<Previsao> previsaos = Collections.singletonList(previsao);
        when(previsaoRepository.findByNomeCidadeAndDataCadastroBetween(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(previsaos);
        when(previsaoMapper.toDto(any(Previsao.class))).thenReturn(previsaoDto);

        List<PrevisaoDto> resultado = previsaoService.buscarPrevisao7Dias("Manaus");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(previsaoDto.nomeCidade(), resultado.get(0).nomeCidade());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar previsão para os próximos 7 dias")
    void deveLancarExcecaoQuandoNaoEncontrarPrevisao7Dias() {
        when(previsaoRepository.findByNomeCidadeAndDataCadastroBetween(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.emptyList());

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            previsaoService.buscarPrevisao7Dias("Manaus");
        });

        assertEquals("Nenhuma previsão encontrada para a cidade: Manaus", exception.getMessage());
    }

    @Test
    @DisplayName("Deve alterar dados meteorológicos com sucesso")
    void deveAlterarDadosMeteorologicosComSucesso() {
        when(previsaoRepository.findById(anyLong())).thenReturn(Optional.of(previsao));
        when(previsaoRepository.save(any(Previsao.class))).thenReturn(previsao);
        when(previsaoMapper.toDto(any(Previsao.class))).thenReturn(previsaoDto);

        PrevisaoDto resultado = previsaoService.alterarDadosMeteorologicos(1L, previsaoDto);

        assertNotNull(resultado);
        assertEquals(previsaoDto.nomeCidade(), resultado.nomeCidade());
    }

    @Test
    @DisplayName("Deve excluir dados meteorológicos com sucesso")
    void deveExcluirDadosMeteorologicosComSucesso() {
        when(previsaoRepository.findById(anyLong())).thenReturn(Optional.of(previsao));
        doNothing().when(previsaoRepository).delete(any(Previsao.class));

        assertDoesNotThrow(() -> previsaoService.excluirDadosMeteorologicos(1L));

        verify(previsaoRepository, times(1)).delete(any(Previsao.class));
    }
}

package com.app.weather.services.impl;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import com.app.weather.exceptions.CamposDosDadosMeteorologicosNaoInformadosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.repositories.CidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CidadeServiceImplTest {

    @InjectMocks
    private CidadeServiceImpl cidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private CidadeMapper cidadeMapper;

    private Cidade cidade;
    private CidadeDto cidadeDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        cidade = Cidade.builder()
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
    @DisplayName("Deve salvar dados meteorológicos com sucesso")
    void deveSalvarDadosMeteorologicosComSucesso() {
        when(cidadeMapper.toEntity(any(CidadeDto.class))).thenReturn(cidade);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);
        when(cidadeMapper.toDto(any(Cidade.class))).thenReturn(cidadeDto);

        CidadeDto resultado = cidadeService.salvarDadosMeteorologicos(cidadeDto);

        assertNotNull(resultado);
        assertEquals(cidadeDto.getNomeCidade(), resultado.getNomeCidade());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    @DisplayName("Deve salvar dados meteorológicos corretamente")
    void deveSalvarDadosMeteorologicosCorretamente() {
        CidadeDto cidadeDto = new CidadeDto();
        cidadeDto.setNomeCidade("São Paulo");
        Cidade cidade = new Cidade();
        when(cidadeMapper.toEntity(any(CidadeDto.class))).thenReturn(cidade);
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);
        when(cidadeMapper.toDto(any(Cidade.class))).thenReturn(cidadeDto);

        CidadeDto resultado = cidadeService.salvarDadosMeteorologicos(cidadeDto);

        assertNotNull(resultado);
        assertEquals("São Paulo", resultado.getNomeCidade());
        verify(cidadeRepository, times(1)).save(any(Cidade.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome da cidade não estiver informado")
    void deveLancarExcecaoQuandoNomeCidadeNaoInformado() {
        CidadeDto cidadeDto = new CidadeDto();
        cidadeDto.setNomeCidade("");

        CamposDosDadosMeteorologicosNaoInformadosException exception = assertThrows(
                CamposDosDadosMeteorologicosNaoInformadosException.class,
                () -> cidadeService.salvarDadosMeteorologicos(cidadeDto)
        );

        assertEquals("O nome da cidade não pode estar vazio.", exception.getMessage());
        verify(cidadeRepository, never()).save(any(Cidade.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar previsão atual")
    void deveLancarExcecaoQuandoNaoEncontrarPrevisaoAtual() {
        when(cidadeRepository.findByDataCadastroAndNomeCidade(any(LocalDate.class), anyString())).thenReturn(Optional.empty());

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeService.buscarPrevisaoAtual("Manaus");
        });

        assertEquals("Nenhuma previsão encontrada para a cidade: Manaus", exception.getMessage());
    }

    @Test
    @DisplayName("Deve buscar previsão para os próximos 7 dias com sucesso")
    void deveBuscarPrevisao7DiasComSucesso() {
        List<Cidade> cidades = Collections.singletonList(cidade);
        when(cidadeRepository.findByNomeCidadeAndDataCadastroBetween(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(cidades);
        when(cidadeMapper.toDto(any(Cidade.class))).thenReturn(cidadeDto);

        List<CidadeDto> resultado = cidadeService.buscarPrevisao7Dias("Manaus");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(cidadeDto.getNomeCidade(), resultado.get(0).getNomeCidade());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar previsão para os próximos 7 dias")
    void deveLancarExcecaoQuandoNaoEncontrarPrevisao7Dias() {
        when(cidadeRepository.findByNomeCidadeAndDataCadastroBetween(anyString(), any(LocalDate.class), any(LocalDate.class))).thenReturn(Collections.emptyList());

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeService.buscarPrevisao7Dias("Manaus");
        });

        assertEquals("Nenhuma previsão encontrada para a cidade: Manaus", exception.getMessage());
    }

    @Test
    @DisplayName("Deve alterar dados meteorológicos com sucesso")
    void deveAlterarDadosMeteorologicosComSucesso() {
        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.of(cidade));
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidade);
        when(cidadeMapper.toDto(any(Cidade.class))).thenReturn(cidadeDto);

        CidadeDto resultado = cidadeService.alterarDadosMeteorologicos(1L, cidadeDto);

        assertNotNull(resultado);
        assertEquals(cidadeDto.getNomeCidade(), resultado.getNomeCidade());
    }

    @Test
    @DisplayName("Deve excluir dados meteorológicos com sucesso")
    void deveExcluirDadosMeteorologicosComSucesso() {
        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.of(cidade));
        doNothing().when(cidadeRepository).delete(any(Cidade.class));

        assertDoesNotThrow(() -> cidadeService.excluirDadosMeteorologicos(1L));

        verify(cidadeRepository, times(1)).delete(any(Cidade.class));
    }
}

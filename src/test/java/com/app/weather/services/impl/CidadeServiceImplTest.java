package com.app.weather.services.impl;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.exceptions.DadosMeteorologicosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.fixtures.CidadeFixture;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.repositories.CidadeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CidadeServiceImplTest {

    @InjectMocks
    private CidadeServiceImpl cidadeService;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private CidadeMapper cidadeMapper;


    @Test
    void salvarDadosMeteorologicos_Success() {

        CidadeDto cidadeDto = new CidadeDto();
        Cidade cidadeSalva = CidadeFixture.gerarCidade();
        when(cidadeRepository.save(any(Cidade.class))).thenReturn(cidadeSalva);

        assertDoesNotThrow(() -> {
            CidadeDto result = cidadeService.salvarDadosMeteorologicos(cidadeDto);
            assertNotNull(result);
        });
    }

    @Test
    void salvarDadosMeteorologicos_ExcecaoLancada() {

        CidadeDto cidadeDto = new CidadeDto();
        cidadeDto.setNomeCidade("Manaus");

        when(cidadeRepository.save(any())).thenThrow(new RuntimeException("Erro de teste"));

        DadosMeteorologicosException exception = assertThrows(DadosMeteorologicosException.class, () -> {
            cidadeService.salvarDadosMeteorologicos(cidadeDto);
        });

        assertEquals("Erro ao salvar dados meteorológicos: Erro de teste", exception.getMessage());
    }

    @Test
    void buscarPrevisaoAtual_Success() {

        String nomeCidade = "Manaus";
        when(cidadeRepository.findByDataCadastroAndNomeCidade(any(LocalDateTime.class), eq(nomeCidade)))
                .thenReturn(Collections.singletonList(new Cidade()));

        assertDoesNotThrow(() -> {
            List<CidadeDto> result = cidadeService.buscarPrevisaoAtual(nomeCidade);
            assertNotNull(result);
            assertFalse(result.isEmpty());
        });
    }

    @Test
    void buscarPrevisaoAtual_ExcecaoLancada() {

        String nomeCidade = "Manaus";

        when(cidadeRepository.findByDataCadastroAndNomeCidade(any(), eq(nomeCidade)))
                .thenThrow(new RuntimeException("Erro de teste"));

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeService.buscarPrevisaoAtual(nomeCidade);
        });

        assertEquals("Erro ao buscar previsão atual: Erro de teste", exception.getMessage());
    }

    @Test
    void buscarPrevisao7Dias_Success() {

        String nomeCidade = "Manaus";
        when(cidadeRepository.findByNomeCidadeAndDataCadastroBetween(eq(nomeCidade), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(new Cidade()));

        assertDoesNotThrow(() -> {
            List<CidadeDto> result = cidadeService.buscarPrevisao7Dias(nomeCidade);
            assertNotNull(result);
            assertFalse(result.isEmpty());
        });
    }

    @Test
    void buscarPrevisao7Dias_NenhumaPrevisaoEncontrada() {

        String nomeCidade = "Manaus";
        LocalDateTime hoje = LocalDateTime.of(2024, 5, 9, 0, 0, 0);
        LocalDateTime umaSemana = hoje.plusDays(7);

        when(cidadeRepository.findByNomeCidadeAndDataCadastroBetween(eq(nomeCidade), any(), any()))
                .thenReturn(Collections.emptyList());

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeService.buscarPrevisao7Dias(nomeCidade);
        });

        assertEquals("Erro ao buscar previsão para os próximos dias: Não foram encontradas previsões para os próximos dias para a cidade: Manaus", exception.getMessage());
    }

    @Test
    void alterarDadosMeteorologicos_IdNaoInformado_ExceptionLancada() {

        CidadeDto cidadeDto = new CidadeDto();

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeService.alterarDadosMeteorologicos(cidadeDto);
        });

        assertEquals("Erro ao alterar dados meteorológicos: ID do dado meteorológico não foi informado", exception.getMessage());
    }

    @Test
    void alterarDadosMeteorologicos_DadoNaoEncontrado_ExceptionLancada() {

        CidadeDto cidadeDto = new CidadeDto();
        cidadeDto.setId(1L);

        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeService.alterarDadosMeteorologicos(cidadeDto);
        });

        assertEquals("Erro ao alterar dados meteorológicos: Dado meteorológico não encontrado para o ID fornecido", exception.getMessage());
    }

    @Test
    void alterarDadosMeteorologicos_Sucesso() throws DadosMeteorologicosNaoInformadosException {

        CidadeDto cidadeDto = new CidadeDto();
        cidadeDto.setId(1L);
        Cidade cidade = new Cidade();
        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.of(cidade));
        when(cidadeMapper.toDto(cidade)).thenReturn(cidadeDto);
        when(cidadeRepository.save(cidade)).thenReturn(cidade);

        CidadeDto result = cidadeService.alterarDadosMeteorologicos(cidadeDto);

        assertNotNull(result);
        assertEquals(cidadeDto, result);
    }

    @Test
    void excluirDadosMeteorologicos_Success() {

        Long id = 1L;
        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.of(new Cidade()));

        assertDoesNotThrow(() -> {
            assertDoesNotThrow(() -> cidadeService.excluirDadosMeteorologicos(id));
        });
    }

    @Test
    void excluirDadosMeteorologicos_CidadeEncontrada() throws DadosMeteorologicosNaoInformadosException {

        Long idCidade = 1L;
        Cidade cidade = new Cidade();
        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.of(cidade));

        cidadeService.excluirDadosMeteorologicos(idCidade);

    }

    @Test
    void excluirDadosMeteorologicos_CidadeNaoEncontrada() {

        Long idCidade = 1L;
        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeService.excluirDadosMeteorologicos(idCidade);
        });

        assertEquals("Erro ao excluir dados meteorológicos: Não encontramos dados com o ID: " + idCidade, exception.getMessage());
    }

    @Test
    void excluirDadosMeteorologicos_ExcecaoDuranteExclusao() {

        Long idCidade = 1L;
        when(cidadeRepository.findById(anyLong())).thenReturn(Optional.empty());

        DadosMeteorologicosNaoInformadosException exception = assertThrows(DadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeService.excluirDadosMeteorologicos(idCidade);
        });

        assertEquals("Erro ao excluir dados meteorológicos: Não encontramos dados com o ID: " + idCidade, exception.getMessage());
    }


}

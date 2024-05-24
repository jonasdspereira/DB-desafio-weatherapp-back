package com.app.weather.controllers;

import com.app.weather.dto.CidadeDto;
import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import com.app.weather.exceptions.CamposDosDadosMeteorologicosNaoInformadosException;
import com.app.weather.services.impl.CidadeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CidadeControllerTest {

    @InjectMocks
    private CidadeController cidadeController;

    @Mock
    private CidadeServiceImpl cidadeService;

    private CidadeDto cidadeDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

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
    @DisplayName("Deve salvar um dado meteorológico com sucesso")
    void deveSalvarDadoMeteorologicoComSucesso() {
        // Arrange
        when(cidadeService.salvarDadosMeteorologicos(any(CidadeDto.class))).thenReturn(cidadeDto);

        // Act
        ResponseEntity<CidadeDto> response = cidadeController.salvarDadosMeteorologicos(cidadeDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cidadeDto, response.getBody());
    }

    @Test
    @DisplayName("Não deve salvar um dado meteorológico quando o nome da cidade estiver vazio")
    void naoDeveSalvarDadoMeteorologicoComNomeCidadeVazio() {
        // Arrange
        CidadeDto cidadeDtoInvalido = CidadeDto.builder().build();
        when(cidadeService.salvarDadosMeteorologicos(any(CidadeDto.class)))
                .thenThrow(new CamposDosDadosMeteorologicosNaoInformadosException("O nome da cidade não pode estar vazio."));

        // Act & Assert
        assertThrows(CamposDosDadosMeteorologicosNaoInformadosException.class, () -> {
            cidadeController.salvarDadosMeteorologicos(cidadeDtoInvalido);
        });
    }

    @Test
    @DisplayName("Deve buscar previsão atual com sucesso")
    void deveBuscarPrevisaoAtualComSucesso() {
        // Arrange
        when(cidadeService.buscarPrevisaoAtual(anyString())).thenReturn(cidadeDto);

        // Act
        ResponseEntity<CidadeDto> response = cidadeController.buscarPrevisaoAtual("Manaus");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidadeDto, response.getBody());
    }

    @Test
    @DisplayName("Deve buscar previsão para os próximos 7 dias com sucesso")
    void deveBuscarPrevisao7DiasComSucesso() {
        // Arrange
        List<CidadeDto> previsoes = Collections.singletonList(cidadeDto);
        when(cidadeService.buscarPrevisao7Dias(anyString())).thenReturn(previsoes);

        // Act
        ResponseEntity<List<CidadeDto>> response = cidadeController.buscarPrevisao7Dias("Manaus");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(previsoes, response.getBody());
    }

    @Test
    @DisplayName("Deve alterar dados meteorológicos com sucesso")
    void deveAlterarDadosMeteorologicosComSucesso() {
        // Arrange
        when(cidadeService.alterarDadosMeteorologicos(any(Long.class), any(CidadeDto.class))).thenReturn(cidadeDto);

        // Act
        ResponseEntity<CidadeDto> response = cidadeController.alterarDadosMeteorologicos(1L, cidadeDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cidadeDto, response.getBody());
    }

    @Test
    @DisplayName("Deve excluir dados meteorológicos com sucesso")
    void deveExcluirDadosMeteorologicosComSucesso() {
        // Arrange
        doNothing().when(cidadeService).excluirDadosMeteorologicos(any(Long.class));

        // Act
        ResponseEntity<Void> response = cidadeController.excluirDadosMeteorologicos(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

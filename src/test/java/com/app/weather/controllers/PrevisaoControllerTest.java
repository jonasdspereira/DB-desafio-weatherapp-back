package com.app.weather.controllers;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.enums.PrevisaoTempo;
import com.app.weather.enums.PrevisaoTurno;
import com.app.weather.exceptions.CamposDosDadosMeteorologicosNaoInformadosException;
import com.app.weather.services.impl.PrevisaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
class PrevisaoControllerTest {

    @InjectMocks
    private PrevisaoController previsaoController;

    @Mock
    private PrevisaoServiceImpl previsaoService;

    private PrevisaoDto previsaoDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        previsaoDto = PrevisaoDto.criarPrevisao("Manaus",LocalDate.now(),PrevisaoTurno.NOITE, PrevisaoTempo.LIMPO,30,25,50,80,120 );
        previsaoController = new PrevisaoController(previsaoService);
    }

    @Test
    @DisplayName("Deve salvar um dado meteorológico com sucesso")
    void deveSalvarDadoMeteorologicoComSucesso() {

        when(previsaoService.salvarDadosMeteorologicos(any(PrevisaoDto.class))).thenReturn(previsaoDto);

        ResponseEntity<PrevisaoDto> response = previsaoController.salvarDadosMeteorologicos(previsaoDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(previsaoDto, response.getBody());
    }

    @Test
    @DisplayName("Não deve salvar um dado meteorológico quando o nome da cidade estiver vazio")
    void naoDeveSalvarDadoMeteorologicoComNomeCidadeVazio() {

        PrevisaoDto previsaoDtoInvalido = PrevisaoDto.criarPrevisao("", LocalDate.now(), PrevisaoTurno.NOITE, PrevisaoTempo.LIMPO, 30, 25, 50, 80, 120);
        when(previsaoService.salvarDadosMeteorologicos(any(PrevisaoDto.class)))
                .thenThrow(new CamposDosDadosMeteorologicosNaoInformadosException("O nome da cidade não pode estar vazio."));


        assertThrows(CamposDosDadosMeteorologicosNaoInformadosException.class, () -> {
            previsaoController.salvarDadosMeteorologicos(previsaoDtoInvalido);
        });
    }

    @Test
    @DisplayName("Deve buscar previsão atual com sucesso")
    void deveBuscarPrevisaoAtualComSucesso() {

        when(previsaoService.buscarPrevisaoAtual(anyString())).thenReturn(previsaoDto);


        ResponseEntity<PrevisaoDto> response = previsaoController.buscarPrevisaoAtual("Manaus");


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(previsaoDto, response.getBody());
    }

    @Test
    @DisplayName("Deve buscar previsão para os próximos 7 dias com sucesso")
    void deveBuscarPrevisao7DiasComSucesso() {

        List<PrevisaoDto> previsoes = Collections.singletonList(previsaoDto);
        when(previsaoService.buscarPrevisao7Dias(anyString())).thenReturn(previsoes);


        ResponseEntity<List<PrevisaoDto>> response = previsaoController.buscarPrevisao7Dias("Manaus");


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(previsoes, response.getBody());
    }

    @Test
    @DisplayName("Deve alterar dados meteorológicos com sucesso")
    void deveAlterarDadosMeteorologicosComSucesso() {

        when(previsaoService.alterarDadosMeteorologicos(any(Long.class), any(PrevisaoDto.class))).thenReturn(previsaoDto);


        ResponseEntity<PrevisaoDto> response = previsaoController.alterarDadosMeteorologicos(1L, previsaoDto);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(previsaoDto, response.getBody());
    }

    @Test
    @DisplayName("Deve excluir dados meteorológicos com sucesso")
    void deveExcluirDadosMeteorologicosComSucesso() {

        doNothing().when(previsaoService).excluirDadosMeteorologicos(any(Long.class));


        ResponseEntity<Void> response = previsaoController.excluirDadosMeteorologicos(1L);


        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

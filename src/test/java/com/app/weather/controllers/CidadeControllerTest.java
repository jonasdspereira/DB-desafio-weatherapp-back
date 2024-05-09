package com.app.weather.controllers;


import com.app.weather.dto.CidadeDto;
import com.app.weather.exceptions.DadosMeteorologicosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.services.impl.CidadeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CidadeControllerTest {

    @Mock
    private CidadeServiceImpl cidadeService;

    @InjectMocks
    private CidadeController cidadeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void salvarDadosMeteorologicos_Successo() throws DadosMeteorologicosException {
        CidadeDto cidadeDto = new CidadeDto();
        when(cidadeService.salvarDadosMeteorologicos(cidadeDto)).thenReturn(cidadeDto);

        ResponseEntity<Object> responseEntity = cidadeController.salvarDadosMeteorologicos(cidadeDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(cidadeDto, responseEntity.getBody());
        verify(cidadeService, times(1)).salvarDadosMeteorologicos(cidadeDto);
    }

    @Test
    void salvarDadosMeteorologicos_Exception() {

        CidadeDto cidadeDto = new CidadeDto();
        when(cidadeService.salvarDadosMeteorologicos(cidadeDto)).thenThrow(new DadosMeteorologicosException("Mensagem de erro"));


        ResponseEntity<Object> responseEntity = cidadeController.salvarDadosMeteorologicos(cidadeDto);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        assertEquals("Erro ao salvar dados meteorológicos: Mensagem de erro", responseEntity.getBody());
    }

    @Test
    void buscarPrevisaoAtual_Ok() throws DadosMeteorologicosNaoInformadosException {
        String nomeCidade = "Manaus";
        when(cidadeService.buscarPrevisaoAtual(nomeCidade)).thenReturn(Collections.singletonList(new CidadeDto()));

        ResponseEntity<List<CidadeDto>> responseEntity = cidadeController.buscarPrevisaoAtual(nomeCidade);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(cidadeService, times(1)).buscarPrevisaoAtual(nomeCidade);
    }

    @Test
    void buscarPrevisaoAtual_NotFound() throws DadosMeteorologicosNaoInformadosException {
        String nomeCidade = "Manaus";
        when(cidadeService.buscarPrevisaoAtual(nomeCidade)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CidadeDto>> responseEntity = cidadeController.buscarPrevisaoAtual(nomeCidade);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(cidadeService, times(1)).buscarPrevisaoAtual(nomeCidade);
    }

    @Test
    void buscarPrevisaoAtual_Exception() throws DadosMeteorologicosNaoInformadosException {

        String nomeCidade = "Nome da Cidade";
        when(cidadeService.buscarPrevisaoAtual(nomeCidade)).thenThrow(new DadosMeteorologicosNaoInformadosException("Mensagem de erro"));

        ResponseEntity<List<CidadeDto>> responseEntity = cidadeController.buscarPrevisaoAtual(nomeCidade);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void buscarPrevisao7Dias_Ok() throws DadosMeteorologicosNaoInformadosException {
        String nomeCidade = "Manaus";
        when(cidadeService.buscarPrevisao7Dias(nomeCidade)).thenReturn(Collections.singletonList(new CidadeDto()));

        ResponseEntity<List<CidadeDto>> responseEntity = cidadeController.buscarPrevisao7Dias(nomeCidade);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(cidadeService, times(1)).buscarPrevisao7Dias(nomeCidade);
    }

    @Test
    void buscarPrevisao7Dias_NotFound() throws DadosMeteorologicosNaoInformadosException {
        String nomeCidade = "Manaus";
        when(cidadeService.buscarPrevisao7Dias(nomeCidade)).thenReturn(Collections.emptyList());

        ResponseEntity<List<CidadeDto>> responseEntity = cidadeController.buscarPrevisao7Dias(nomeCidade);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(cidadeService, times(1)).buscarPrevisao7Dias(nomeCidade);
    }

    @Test
    void buscarPrevisao7Dias_Exception() throws DadosMeteorologicosNaoInformadosException {

        String nomeCidade = "Nome da Cidade";
        when(cidadeService.buscarPrevisao7Dias(nomeCidade)).thenThrow(new DadosMeteorologicosNaoInformadosException("Mensagem de erro"));

        ResponseEntity<List<CidadeDto>> responseEntity = cidadeController.buscarPrevisao7Dias(nomeCidade);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void alterarDadosMeteorologicos_Successo() throws DadosMeteorologicosNaoInformadosException {

        Long id = 1L;
        CidadeDto dto = new CidadeDto();
        dto.setId(id);

        CidadeDto cidadeDtoAtualizado = new CidadeDto();
        cidadeDtoAtualizado.setId(id);
        when(cidadeService.alterarDadosMeteorologicos(dto)).thenReturn(cidadeDtoAtualizado);

        ResponseEntity<Object> responseEntity = cidadeController.alterarDadosMeteorologicos(id, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cidadeDtoAtualizado, responseEntity.getBody());
        verify(cidadeService, times(1)).alterarDadosMeteorologicos(dto);
    }

    @Test
    void alterarDadosMeteorologicos_IfDtoNull() {

        Long id = 1L;
        CidadeDto dto = null;

        ResponseEntity<Object> responseEntity = cidadeController.alterarDadosMeteorologicos(id, dto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Erro ao atualizar dados meteorológicos: ID do dado meteorológico não foi informado", responseEntity.getBody());
        verifyNoInteractions(cidadeService);
    }

    @Test
    void alterarDadosMeteorologicos_IfIdNotMatch() {

        Long id = 1L;
        CidadeDto dto = new CidadeDto();
        dto.setId(2L);

        ResponseEntity<Object> responseEntity = cidadeController.alterarDadosMeteorologicos(id, dto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Erro ao atualizar dados meteorológicos: O ID dos dados meteorológicos não corresponde ao ID fornecido na URL", responseEntity.getBody());
        verifyNoInteractions(cidadeService);
    }

    @Test
    void alterarDadosMeteorologicos_TryCatch_Success() throws DadosMeteorologicosNaoInformadosException {

        Long id = 1L;
        CidadeDto dto = new CidadeDto();
        dto.setId(id);


        CidadeDto cidadeDtoAtualizado = new CidadeDto();
        cidadeDtoAtualizado.setId(id);
        when(cidadeService.alterarDadosMeteorologicos(dto)).thenReturn(cidadeDtoAtualizado);

        ResponseEntity<Object> responseEntity = cidadeController.alterarDadosMeteorologicos(id, dto);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cidadeDtoAtualizado, responseEntity.getBody());
        verify(cidadeService, times(1)).alterarDadosMeteorologicos(dto);
    }

    @Test
    void alterarDadosMeteorologicos_TryCatch_Exception() throws DadosMeteorologicosNaoInformadosException {

        Long id = 1L;
        CidadeDto dto = new CidadeDto();
        dto.setId(id);


        when(cidadeService.alterarDadosMeteorologicos(dto)).thenThrow(new DadosMeteorologicosNaoInformadosException("Erro ao atualizar dados meteorológicos"));

        ResponseEntity<Object> responseEntity = cidadeController.alterarDadosMeteorologicos(id, dto);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Erro ao atualizar dados meteorológicos: Erro ao atualizar dados meteorológicos", responseEntity.getBody());
        verify(cidadeService, times(1)).alterarDadosMeteorologicos(dto);
    }

    @Test
    void excluirDadosMeteorologicos_TryCatch_Success() throws DadosMeteorologicosNaoInformadosException {

        Long id = 1L;

        ResponseEntity<Object> responseEntity = cidadeController.excluirDadosMeteorologicos(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(cidadeService, times(1)).excluirDadosMeteorologicos(id);
    }

    @Test
    void excluirDadosMeteorologicos_TryCatch_Exception() throws DadosMeteorologicosNaoInformadosException {

        Long id = 1L;

        doThrow(new DadosMeteorologicosNaoInformadosException("Dados meteorológicos não encontrados")).when(cidadeService).excluirDadosMeteorologicos(id);

        ResponseEntity<Object> responseEntity = cidadeController.excluirDadosMeteorologicos(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Erro ao excluir dados meteorológicos: Dados meteorológicos não encontrados", responseEntity.getBody());
        verify(cidadeService, times(1)).excluirDadosMeteorologicos(id);
    }




}

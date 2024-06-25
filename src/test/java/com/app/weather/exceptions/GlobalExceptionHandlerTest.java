package com.app.weather.exceptions;

import com.app.weather.controllers.PrevisaoController;
import com.app.weather.dto.PrevisaoDto;
import com.app.weather.services.impl.PrevisaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataJpaTest
@ActiveProfiles("test")
@WebMvcTest(PrevisaoController.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrevisaoServiceImpl previsaoService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST quando CamposDosDadosMeteorologicosNaoInformadosException for lançada")
    void deveRetornarBadRequestQuandoCamposDosDadosMeteorologicosNaoInformadosExceptionForLancada() throws Exception {
        Mockito.when(previsaoService.salvarDadosMeteorologicos(Mockito.any(PrevisaoDto.class)))
                .thenThrow(new CamposDosDadosMeteorologicosNaoInformadosException("O nome da cidade não pode estar vazio."));

        mockMvc.perform(post("/previsao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nomeCidade\": \"\", \"dataCadastro\": \"2023-05-23\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("O nome da cidade não pode estar vazio."))
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    @DisplayName("Deve retornar NOT_FOUND quando DadosMeteorologicosNaoInformadosException for lançada")
    void deveRetornarNotFoundQuandoDadosMeteorologicosNaoInformadosExceptionForLancada() throws Exception {
        Mockito.when(previsaoService.buscarPrevisaoAtual(Mockito.anyString()))
                .thenThrow(new DadosMeteorologicosNaoInformadosException("Nenhuma previsão encontrada para a cidade."));

        mockMvc.perform(get("/previsao/Manaus"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Nenhuma previsão encontrada para a cidade."));
    }

    @Test
    @DisplayName("Deve retornar INTERNAL_SERVER_ERROR quando Exception genérica for lançada")
    void deveRetornarInternalServerErrorQuandoExceptionGenericaForLancada() throws Exception {
        Mockito.when(previsaoService.buscarPrevisaoAtual(Mockito.anyString()))
                .thenThrow(new RuntimeException("Erro interno do servidor."));

        mockMvc.perform(get("/previsao/Manaus"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andExpect(jsonPath("$.message").value("Erro interno do servidor."));
    }
}

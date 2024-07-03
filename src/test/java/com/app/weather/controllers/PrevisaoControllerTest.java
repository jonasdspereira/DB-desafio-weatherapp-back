package com.app.weather.controllers;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.services.impl.PrevisaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static com.app.weather.enums.PrevisaoTempo.CHUVOSO;
import static com.app.weather.enums.PrevisaoTurno.TARDE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@WebMvcTest(PrevisaoController.class)
@AutoConfigureMockMvc
class PrevisaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrevisaoServiceImpl previsaoService;

    private PrevisaoDto previsaoDto;

    @BeforeEach
    void setup() {
        previsaoDto = new PrevisaoDto(1L, "Cidade Teste", LocalDate.of(2024, 7, 2), TARDE, CHUVOSO, 30, 25, 20, 50, 10);
    }

    @Test
    void salvarDadosMeteorologicos() throws Exception {
        when(previsaoService.salvarDadosMeteorologicos(any(PrevisaoDto.class))).thenReturn(previsaoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/previsoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nomeCidade\": \"Cidade Teste\", \"dataCadastro\": \"02/07/2024\", \"previsaoTurno\": \"TARDE\", \"previsaoTempo\": \"CHUVOSO\", \"temperaturaMaxima\": 30, \"temperaturaMinima\": 25, \"precipitacao\": 20, \"umidade\": 50, \"velocidadeDoVento\": 10 }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeCidade").value("Cidade Teste"));
    }

    @Test
    void buscarPrevisoes() throws Exception {
        when(previsaoService.buscarPrevisoes()).thenReturn(Collections.singletonList(previsaoDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/previsoes/todas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeCidade").value("Cidade Teste"));
    }

    @Test
    void buscarPrevisaoPeloNome() throws Exception {
        when(previsaoService.buscarPrevisaoPeloNome("Cidade Teste")).thenReturn(Collections.singletonList(previsaoDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/previsoes/Cidade Teste")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeCidade").value("Cidade Teste"));
    }

    @Test
    void buscarPrevisao() throws Exception {
        when(previsaoService.buscarPrevisao(1L)).thenReturn(previsaoDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/previsoes/previsao/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeCidade").value("Cidade Teste"));
    }

    @Test
    void alterarDadosMeteorologicos() throws Exception {
        when(previsaoService.alterarDadosMeteorologicos(any(Long.class), any(PrevisaoDto.class))).thenReturn(previsaoDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/previsoes/previsao/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nomeCidade\": \"Cidade Teste\", \"dataCadastro\": \"02/07/2024\", \"previsaoTurno\": \"TARDE\", \"previsaoTempo\": \"CHUVOSO\", \"temperaturaMaxima\": 30, \"temperaturaMinima\": 25, \"precipitacao\": 20, \"umidade\": 50, \"velocidadeDoVento\": 10 }")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeCidade").value("Cidade Teste"));
    }

    @Test
    void excluirDadosMeteorologicos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/previsoes/previsao/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

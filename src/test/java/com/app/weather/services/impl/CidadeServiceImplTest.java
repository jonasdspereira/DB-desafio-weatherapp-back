package com.app.weather.services.impl;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.fixtures.CidadeFixture;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.repositories.CidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CidadeServiceImplTest {

    @InjectMocks
    private CidadeServiceImpl cidadeServiceImpl;

    @Mock
    private CidadeRepository cidadeRepository;

    @Mock
    private CidadeMapper cidadeMapper;


    private CidadeDto cidadeDto;

    private Cidade cidade;



    @BeforeEach
    void setup() {
        cidade = CidadeFixture.gerarCidade();
    }

    @Test
    @DisplayName("Deve criar cidade")
    void deveCriarCidade() {

    }

    @Test
    @DisplayName("Deve encontrar cidade pelo nome")
    void deveEncontrarCidadePeloNome () throws DadosMeteorologicosNaoInformadosException {

        cidadeDto = new CidadeDto();
        cidadeDto.setId(1L);
        cidadeDto.setNomeCidade("Manaus");
        LocalDateTime dataAtual = LocalDateTime.of(2024, Month.MAY,8,00,00,00);


        when(this.cidadeRepository.findByDataCadastroAndNomeCidade(dataAtual,"Manaus")).thenReturn(List.of(cidade));

        when(this.cidadeMapper.toDto(cidade)).thenReturn(cidadeDto);

        List<CidadeDto> cidadeEncontrada = this.cidadeServiceImpl.buscarPrevisaoAtual("Manaus");

        assertFalse(cidadeEncontrada.isEmpty());
        assertEquals("Manaus", cidadeEncontrada.get(0).getNomeCidade());

    }


}

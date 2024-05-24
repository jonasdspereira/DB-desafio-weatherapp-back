package com.app.weather.repositories;

import com.app.weather.entities.Cidade;
import com.app.weather.enums.CidadeTempo;
import com.app.weather.enums.CidadeTurno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    private Cidade cidade;

    @BeforeEach
    void setup() {
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
        cidadeRepository.save(cidade);
    }

    @Test
    @DisplayName("Deve encontrar uma cidade pelo nome e data de cadastro")
    void deveEncontrarCidadePorNomeEDataCadastro() {
        // Arrange
        LocalDate dataCadastro = LocalDate.now();
        String nomeCidade = "Manaus";

        // Act
        Optional<Cidade> foundCidade = cidadeRepository.findByDataCadastroAndNomeCidade(dataCadastro, nomeCidade);

        // Assert
        assertThat(foundCidade).isPresent();
        assertThat(foundCidade.get().getNomeCidade()).isEqualTo(nomeCidade);
        assertThat(foundCidade.get().getDataCadastro()).isEqualTo(dataCadastro);
    }

    @Test
    @DisplayName("Deve encontrar cidades pelo nome e intervalo de datas")
    void deveEncontrarCidadesPorNomeEIntervaloDeDatas() {
        // Arrange
        String nomeCidade = "Manaus";
        LocalDate dataInicio = LocalDate.now().minusDays(1);
        LocalDate dataFim = LocalDate.now().plusDays(1);

        // Act
        List<Cidade> foundCidades = cidadeRepository.findByNomeCidadeAndDataCadastroBetween(nomeCidade, dataInicio, dataFim);

        // Assert
        assertThat(foundCidades).isNotEmpty();
        assertThat(foundCidades).contains(cidade);
    }
}

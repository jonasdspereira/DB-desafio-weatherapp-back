package com.app.weather.repositories;

import com.app.weather.entities.Previsao;
import com.app.weather.enums.PrevisaoTempo;
import com.app.weather.enums.PrevisaoTurno;
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
class PrevisaoRepositoryTest {

    @Autowired
    private PrevisaoRepository previsaoRepository;

    private Previsao previsao;

    @BeforeEach
    void setup() {
        previsao = Previsao.builder()
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
        previsaoRepository.save(previsao);
    }

    @Test
    @DisplayName("Deve encontrar uma cidade pelo nome e data de cadastro")
    void deveEncontrarCidadePorNomeEDataCadastro() {
        LocalDate dataCadastro = LocalDate.now();
        String nomeCidade = "Manaus";

        Optional<Previsao> resultado = previsaoRepository.findByDataCadastroAndNomeCidade(dataCadastro, nomeCidade);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNomeCidade()).isEqualTo(nomeCidade);
        assertThat(resultado.get().getDataCadastro()).isEqualTo(dataCadastro);
    }

    @Test
    @DisplayName("Deve encontrar cidades pelo nome e intervalo de datas")
    void deveEncontrarCidadesPorNomeEIntervaloDeDatas() {

        String nomeCidade = "Manaus";
        LocalDate dataInicio = LocalDate.now().minusDays(1);
        LocalDate dataFim = LocalDate.now().plusDays(1);

        List<Previsao> resultado = previsaoRepository.findByNomeCidadeAndDataCadastroBetween(nomeCidade, dataInicio, dataFim);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).contains(previsao);
    }
}
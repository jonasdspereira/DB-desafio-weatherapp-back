package com.app.weather.repositories;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.entities.Previsao;
import com.app.weather.enums.PrevisaoTempo;
import com.app.weather.enums.PrevisaoTurno;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class PrevisaoRepositoryTest {

    @Autowired
    PrevisaoRepository previsaoRepository;

    @Autowired
    EntityManager entityManager;

    private Previsao previsao1;
    private Previsao previsao2;

    @BeforeEach
    void setup() {
        previsao1 = criarPrevisao(new PrevisaoDto(1L, "Manaus", LocalDate.of(2024, 7, 2), PrevisaoTurno.NOITE, PrevisaoTempo.CHUVOSO, 25, 15, 20, 1, 1));
        previsao2 = criarPrevisao(new PrevisaoDto(2L, "São Paulo", LocalDate.of(2024, 7, 3), PrevisaoTurno.MANHA, PrevisaoTempo.LIMPO, 30, 20, 0, 2, 2));
    }

    @Transactional
    @AfterEach
    void tearDown() {
        entityManager.remove(previsao1);
        entityManager.remove(previsao2);
        entityManager.flush();
    }

    @Test
    @DisplayName("Deve buscar a previsão pelo nome quando encontrada")
    void findByNomeCidadeSuccess() {
        String nomeCidade = "Manaus";

        List<Previsao> previsaoEncontrada = previsaoRepository.findByNomeCidade(nomeCidade);

        assertThat(previsaoEncontrada).isNotEmpty();
        assertThat(previsaoEncontrada).contains(previsao1);
    }

    @Test
    @DisplayName("Não deve buscar a previsão pelo nome quando não encontrada")
    void findByNomeCidadeFail() {
        String nomeCidade = "Cidade Inexistente";

        List<Previsao> previsaoEncontrada = previsaoRepository.findByNomeCidade(nomeCidade);

        assertThat(previsaoEncontrada).isEmpty();
    }

    private Previsao criarPrevisao(PrevisaoDto dto) {
        Previsao novaPrevisao = new Previsao(dto);
        entityManager.persist(novaPrevisao);
        return novaPrevisao;
    }
}

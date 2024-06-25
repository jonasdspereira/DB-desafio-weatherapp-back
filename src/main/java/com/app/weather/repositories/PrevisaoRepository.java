package com.app.weather.repositories;


import com.app.weather.entities.Previsao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrevisaoRepository extends JpaRepository<Previsao, Long> {

    List<Previsao> findByNomeCidade(String nomeCidade);
}

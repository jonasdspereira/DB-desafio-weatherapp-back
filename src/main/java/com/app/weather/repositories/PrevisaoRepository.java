package com.app.weather.repositories;


import com.app.weather.entities.Previsao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrevisaoRepository extends JpaRepository<Previsao, Long> {

    List<Previsao> findByNomeCidade(String nomeCidade);
}

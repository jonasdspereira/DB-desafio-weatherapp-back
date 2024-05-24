package com.app.weather.repositories;


import com.app.weather.entities.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    Optional<Cidade> findByDataCadastroAndNomeCidade(LocalDate dataCadastro, String nomeCidade);

    List<Cidade> findByNomeCidadeAndDataCadastroBetween(String nomeCidade, LocalDate dataInicio, LocalDate dataFim);
}

package com.app.weather.repositories;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    @Query("SELECT c FROM Cidade c WHERE c.nomeCidade = :nomeCidade AND c.dataCadastro = :dataCadastro")
    List<Cidade> findByDataCadastroAndNomeCidade(@Param("dataCadastro") LocalDateTime dataCadastro, @Param("nomeCidade") String nomeCidade);

    List<Cidade> findByDataCadastroBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
}

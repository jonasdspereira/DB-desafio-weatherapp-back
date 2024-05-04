package com.app.weather.repositories;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    @Query("SELECT c FROM Cidade c WHERE c.nomeCidade = :nomeCidade")
    List<Cidade> buscarCidade(@Param("nomeCidade") String nomeCidade);
}

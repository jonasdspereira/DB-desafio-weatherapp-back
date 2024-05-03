package com.app.weather.repositories;

import com.app.weather.dto.request.CidadeRequestDto;
import com.app.weather.entities.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}

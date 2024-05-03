package com.app.weather.services.impl;

import com.app.weather.dto.request.CidadeRequestDto;
import com.app.weather.entities.Cidade;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.repositories.CidadeRepository;
import com.app.weather.services.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    @Override
    public CidadeRequestDto salvarCidade(CidadeRequestDto cidadeRequestDto) {
        Cidade cidade = CidadeMapper.MAPPER.toEntity(cidadeRequestDto);
        Cidade cidadeSalva = cidadeRepository.save(cidade);

        return CidadeMapper.MAPPER.toDto(cidadeSalva);
    }
}

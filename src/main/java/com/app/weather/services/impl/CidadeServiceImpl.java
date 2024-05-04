package com.app.weather.services.impl;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.repositories.CidadeRepository;
import com.app.weather.services.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    @Override
    public CidadeDto salvarCidade(CidadeDto cidadeDto) {
        Cidade cidade = CidadeMapper.MAPPER.toEntity(cidadeDto);
        Cidade cidadeSalva = cidadeRepository.save(cidade);

        return CidadeMapper.MAPPER.toDto(cidadeSalva);
    }

    @Override
    public List<CidadeDto> buscarCidade(String nomeCidade) {
        List<Cidade> cidades = cidadeRepository.buscarCidade(nomeCidade);
        return cidades.stream().map(cidadeMapper::toDto).collect(Collectors.toList());
    }



}

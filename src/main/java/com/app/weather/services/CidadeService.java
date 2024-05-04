package com.app.weather.services;

import com.app.weather.dto.CidadeDto;

import java.util.List;

public interface CidadeService {

    CidadeDto salvarCidade (CidadeDto cidadeDto);

    List<CidadeDto> buscarCidade (String nomeCidade);

}



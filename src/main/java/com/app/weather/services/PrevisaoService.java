package com.app.weather.services;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.entities.Previsao;

import java.util.List;

public interface PrevisaoService {

    PrevisaoDto salvarDadosMeteorologicos (PrevisaoDto previsaoDto);

    List<PrevisaoDto> buscarPrevisoes(String nomeCidade);

    PrevisaoDto buscarPrevisaoAtual (String nomeCidade);

    List<PrevisaoDto> buscarPrevisao7Dias(String nomeCidade);

    PrevisaoDto alterarDadosMeteorologicos(Long id, PrevisaoDto previsaoDto);

    void excluirDadosMeteorologicos(Long id);

    Previsao buscar(Long id);

}



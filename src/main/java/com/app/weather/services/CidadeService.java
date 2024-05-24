package com.app.weather.services;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;

import java.util.List;

public interface CidadeService {

    CidadeDto salvarDadosMeteorologicos (CidadeDto cidadeDto);

    CidadeDto buscarPrevisaoAtual (String nomeCidade);

    List<CidadeDto> buscarPrevisao7Dias(String nomeCidade);

    CidadeDto alterarDadosMeteorologicos(Long id, CidadeDto cidadeDto);

    void excluirDadosMeteorologicos(Long id);

    Cidade buscar(Long id);

}



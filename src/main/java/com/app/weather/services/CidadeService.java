package com.app.weather.services;

import com.app.weather.dto.CidadeDto;
import com.app.weather.exceptions.DadosMeteorologicosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;

import java.util.List;

public interface CidadeService {

    CidadeDto salvarDadosMeteorologicos (CidadeDto cidadeDto) throws DadosMeteorologicosException;

    List<CidadeDto> buscarPrevisaoAtual (String nomeCidade) throws DadosMeteorologicosNaoInformadosException;

    List<CidadeDto> buscarPrevisao7Dias(String nomeCidade) throws DadosMeteorologicosNaoInformadosException;

    CidadeDto alterarDadosMeteorologicos(CidadeDto cidadeDto) throws DadosMeteorologicosNaoInformadosException;

    void excluirDadosMeteorologicos(Long id) throws DadosMeteorologicosNaoInformadosException;
}



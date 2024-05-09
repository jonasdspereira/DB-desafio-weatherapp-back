package com.app.weather.services;

import com.app.weather.dto.CidadeDto;
import com.app.weather.exceptions.CidadeNaoEncontradaException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;

import java.util.List;

public interface CidadeService {

    CidadeDto salvarDadosMeteorologicos (CidadeDto cidadeDto) throws CidadeNaoEncontradaException;

    List<CidadeDto> buscarPrevisaoAtual (String nomeCidade) throws DadosMeteorologicosNaoInformadosException;

    List<CidadeDto> buscarPrevisao7Dias() throws DadosMeteorologicosNaoInformadosException;

    CidadeDto alterarDadosMeteorologicos(CidadeDto cidadeDto) throws DadosMeteorologicosNaoInformadosException;

    void excluirDadosMeteorologicos(Long id) throws DadosMeteorologicosNaoInformadosException;
}



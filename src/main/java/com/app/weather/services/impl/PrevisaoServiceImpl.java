package com.app.weather.services.impl;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.entities.Previsao;
import com.app.weather.exceptions.CamposDosDadosMeteorologicosNaoInformadosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.exceptions.PrevisaoNaoEncontradaException;
import com.app.weather.mapper.PrevisaoMapper;
import com.app.weather.repositories.PrevisaoRepository;
import com.app.weather.services.PrevisaoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrevisaoServiceImpl implements PrevisaoService {

    private final PrevisaoRepository previsaoRepository;
    private final PrevisaoMapper previsaoMapper;


    public PrevisaoServiceImpl(PrevisaoRepository previsaoRepository, PrevisaoMapper previsaoMapper) {
        this.previsaoRepository = previsaoRepository;
        this.previsaoMapper = previsaoMapper;
    }

    @Override
    public PrevisaoDto salvarDadosMeteorologicos(PrevisaoDto previsaoDto) {
        try {
            Optional.ofNullable(previsaoDto.nomeCidade())
                    .filter(nome -> !nome.isEmpty())
                    .orElseThrow(() -> new CamposDosDadosMeteorologicosNaoInformadosException("O nome da cidade não pode estar vazio."));
            Previsao previsao = previsaoMapper.toEntity(previsaoDto);
            Previsao previsaoSalva = previsaoRepository.save(previsao);
            return previsaoMapper.toDto(previsaoSalva);
        } catch (CamposDosDadosMeteorologicosNaoInformadosException e) {
            throw e;
        }
    }


    @Override
    public List<PrevisaoDto> buscarPrevisoes() throws PrevisaoNaoEncontradaException {
        try {
            List<Previsao> previsoes = previsaoRepository.findAll();

            if (previsoes.isEmpty()) {
                throw new PrevisaoNaoEncontradaException("Nenhuma previsão encontrada.");
            }

            return previsoes.stream()
                    .map(previsaoMapper::toDto)
                    .collect(Collectors.toList());
        } catch (PrevisaoNaoEncontradaException ex) {
            throw ex;
        }
    }

    @Override
    public PrevisaoDto buscarPrevisao(Long id) {
        Previsao previsao = buscar(id);
        return previsaoMapper.toDto(previsao);
    }

    public List<PrevisaoDto> buscarPrevisaoPeloNome(String nomeCidade) {
        List<Previsao> previsoes = previsaoRepository.findByNomeCidade(nomeCidade);
        return previsoes.stream()
                .map(previsaoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PrevisaoDto alterarDadosMeteorologicos(Long id, PrevisaoDto previsaoDto) {
        Previsao previsao = buscar(id);

        previsao.setNomeCidade(previsaoDto.nomeCidade());
        previsao.setDataCadastro(previsaoDto.dataCadastro());
        previsao.setPrevisaoTurno(previsaoDto.previsaoTurno());
        previsao.setPrevisaoTempo(previsaoDto.previsaoTempo());
        previsao.setTemperaturaMaxima(previsaoDto.temperaturaMaxima());
        previsao.setTemperaturaMinima(previsaoDto.temperaturaMinima());
        previsao.setPrecipitacao(previsaoDto.precipitacao());
        previsao.setUmidade(previsaoDto.umidade());
        previsao.setVelocidadeDoVento(previsaoDto.velocidadeDoVento());

        return previsaoMapper.toDto(previsaoRepository.save(previsao));
    }

    @Override
    public void excluirDadosMeteorologicos(Long id) {
        previsaoRepository.deleteById(id);

    }

    public Previsao buscar(Long id) {
        return previsaoRepository.findById(id)
                .orElseThrow(() -> new PrevisaoNaoEncontradaException("Previsão não encontrada para o ID: " + id));
    }
}
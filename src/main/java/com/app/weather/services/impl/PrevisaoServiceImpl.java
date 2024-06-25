package com.app.weather.services.impl;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.entities.Previsao;
import com.app.weather.exceptions.CamposDosDadosMeteorologicosNaoInformadosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.mapper.PrevisaoMapper;
import com.app.weather.repositories.PrevisaoRepository;
import com.app.weather.services.PrevisaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrevisaoServiceImpl implements PrevisaoService {

    private final PrevisaoRepository previsaoRepository;
    private final PrevisaoMapper previsaoMapper;

    @Autowired
    public PrevisaoServiceImpl(PrevisaoRepository previsaoRepository, PrevisaoMapper previsaoMapper) {
        this.previsaoRepository = previsaoRepository;
        this.previsaoMapper = previsaoMapper;
    }

    @Override
    public PrevisaoDto salvarDadosMeteorologicos(PrevisaoDto previsaoDto) {

        Optional.ofNullable(previsaoDto.nomeCidade())
                .filter(nome -> !nome.isEmpty())
                .orElseThrow(() -> new CamposDosDadosMeteorologicosNaoInformadosException("O nome da cidade não pode estar vazio."));
        Previsao previsao = previsaoMapper.toEntity(previsaoDto);
        Previsao previsaoSalva = previsaoRepository.save(previsao);
        return previsaoMapper.toDto(previsaoSalva);
    }


    @Override
    public List<PrevisaoDto> buscarPrevisoes() {
        List<Previsao> previsoes = previsaoRepository.findAll();

        return previsoes.stream()
                .map(previsaoMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<PrevisaoDto> buscarPrevisao(Long id) {
        Optional<Previsao> previsao = previsaoRepository.findById(id);
        return previsao.map(previsaoMapper::toDto)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
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

    @Override
    public Previsao buscar(Long id) {

        return previsaoRepository.findById(id)
                .orElseThrow(() -> new DadosMeteorologicosNaoInformadosException("Erro ao encontrar previsao com o id " + id));

    }
}

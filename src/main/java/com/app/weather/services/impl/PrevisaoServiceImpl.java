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
    public List<PrevisaoDto> buscarPrevisoes(String nomeCidade) {
        String nomeCidadeFinal = nomeCidade.replace("-", " ");
        List<Previsao> previsoes = previsaoRepository.findPrevisoesByNomeCidade(nomeCidadeFinal);

        if (previsoes.isEmpty()) {
            throw new DadosMeteorologicosNaoInformadosException("Nenhuma previsão encontrada para a cidade: " + nomeCidadeFinal);
        }
        return previsoes.stream()
                .map(previsaoMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public PrevisaoDto buscarPrevisaoAtual(String nomeCidade) {
        String nomeCidadeFinal = nomeCidade.replace("-", " ");
        LocalDate dataAtual = LocalDate.now();
        Optional<Previsao> previsao = previsaoRepository.findByDataCadastroAndNomeCidade(dataAtual, nomeCidadeFinal);
        return previsao.map(previsaoMapper::toDto)
                .orElseThrow(() -> new DadosMeteorologicosNaoInformadosException("Nenhuma previsão encontrada para a cidade: " + nomeCidadeFinal));
    }

    @Override
    public List<PrevisaoDto> buscarPrevisao7Dias(String nomeCidade) {
        String nomeCidadeFinal = nomeCidade.replace("-", " ");
        LocalDate hoje = LocalDate.now();
        LocalDate umaSemana = hoje.plusDays(7);
        List<Previsao> previsoes = previsaoRepository.findByNomeCidadeAndDataCadastroBetween(nomeCidadeFinal, hoje, umaSemana);
        if (previsoes.isEmpty()) {
            throw new DadosMeteorologicosNaoInformadosException("Nenhuma previsão encontrada para a cidade: " + nomeCidadeFinal);
        }
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
        Previsao previsao = buscar(id);
        previsaoRepository.delete(previsao);

    }

    @Override
    public Previsao buscar(Long id) {

        return previsaoRepository.findById(id)
                .orElseThrow(() -> new DadosMeteorologicosNaoInformadosException("Erro ao encontrar previsao com o id " + id));

    }
}

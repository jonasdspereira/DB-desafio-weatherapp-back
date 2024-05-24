package com.app.weather.services.impl;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.exceptions.CamposDosDadosMeteorologicosNaoInformadosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.repositories.CidadeRepository;
import com.app.weather.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CidadeServiceImpl implements CidadeService {

    private final CidadeRepository cidadeRepository;
    private final CidadeMapper cidadeMapper;

    @Autowired
    public CidadeServiceImpl(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper  ) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    @Override
    public CidadeDto salvarDadosMeteorologicos(CidadeDto cidadeDto) {
        Optional.ofNullable(cidadeDto.getNomeCidade())
                .filter(nome -> !nome.isEmpty())
                .orElseThrow(() -> new CamposDosDadosMeteorologicosNaoInformadosException("O nome da cidade não pode estar vazio."));

        Cidade cidade = cidadeMapper.toEntity(cidadeDto);
        Cidade cidadeSalva = cidadeRepository.save(cidade);
        return cidadeMapper.toDto(cidadeSalva);
    }


    @Override
    public CidadeDto buscarPrevisaoAtual(String nomeCidade) {
        String nomeCidadeFinal = nomeCidade.replace("-", " ");
        LocalDate dataAtual = LocalDate.now();
        Optional<Cidade> previsao = cidadeRepository.findByDataCadastroAndNomeCidade(dataAtual, nomeCidadeFinal);
        return previsao.map(cidadeMapper::toDto)
                .orElseThrow(() -> new DadosMeteorologicosNaoInformadosException("Nenhuma previsão encontrada para a cidade: " + nomeCidadeFinal));
    }

    @Override
    public List<CidadeDto> buscarPrevisao7Dias(String nomeCidade) {
        String nomeCidadeFinal = nomeCidade.replace("-", " ");
        LocalDate hoje = LocalDate.now();
        LocalDate umaSemana = hoje.plusDays(7);
        List<Cidade> previsoes = cidadeRepository.findByNomeCidadeAndDataCadastroBetween(nomeCidadeFinal, hoje, umaSemana);
        if (previsoes.isEmpty()) {
            throw new DadosMeteorologicosNaoInformadosException("Nenhuma previsão encontrada para a cidade: " + nomeCidadeFinal);
        }
        return previsoes.stream()
                .map(cidadeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CidadeDto alterarDadosMeteorologicos(Long id, CidadeDto cidadeDto) {
        Cidade cidade = buscar(id);

        cidade.setNomeCidade(cidadeDto.getNomeCidade());
        cidade.setDataCadastro(cidadeDto.getDataCadastro());
        cidade.setCidadeTurno(cidadeDto.getCidadeTurno());
        cidade.setCidadeTempo(cidadeDto.getCidadeTempo());
        cidade.setTemperaturaMaxima(cidadeDto.getTemperaturaMaxima());
        cidade.setTemperaturaMinima(cidadeDto.getTemperaturaMinima());
        cidade.setPrecipitacao(cidadeDto.getPrecipitacao());
        cidade.setUmidade(cidadeDto.getUmidade());
        cidade.setVelocidadeDoVento(cidadeDto.getVelocidadeDoVento());

        return cidadeMapper.toDto(cidadeRepository.save(cidade));
    }

    @Override
    public void excluirDadosMeteorologicos(Long id) {
        Cidade cidade = buscar(id);
        cidadeRepository.delete(cidade);

    }

    @Override
    public Cidade buscar(Long id) {

        return cidadeRepository.findById(id)
                .orElseThrow(() -> new DadosMeteorologicosNaoInformadosException("Erro ao encontrar previsao com o id " + id));

    }
}

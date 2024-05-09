package com.app.weather.services.impl;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.exceptions.CidadeNaoEncontradaException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.repositories.CidadeRepository;
import com.app.weather.services.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CidadeServiceImpl implements CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeMapper cidadeMapper;

    public CidadeDto salvarDadosMeteorologicos(CidadeDto cidadeDto) throws CidadeNaoEncontradaException {
        Cidade cidade = CidadeMapper.MAPPER.toEntity(cidadeDto);
        Cidade cidadeSalva = cidadeRepository.save(cidade);
        cidadeDto = cidadeMapper.MAPPER.toDto(cidadeSalva);
        return (cidadeDto);
    }

    @Override
    public List<CidadeDto> buscarPrevisaoAtual(String nomeCidade) throws DadosMeteorologicosNaoInformadosException {
        LocalDateTime dataAtual = LocalDateTime.of(2024, Month.MAY, 8, 00, 00, 00);

        List<Cidade> previsao = cidadeRepository.findByDataCadastroAndNomeCidade(dataAtual, nomeCidade);

        List<CidadeDto> previsaoDto = previsao.stream().map(cidadeMapper::toDto).collect(Collectors.toList());
        return previsaoDto;
    }

    @Override
    public List<CidadeDto> buscarPrevisao7Dias() {
        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime umaSemana = hoje.plusDays(7);

        List<Cidade> previsoes = cidadeRepository.findByDataCadastroBetween(hoje, umaSemana);

        return previsoes.stream()
                .map(cidadeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CidadeDto alterarDadosMeteorologicos(CidadeDto cidadeDto) throws DadosMeteorologicosNaoInformadosException {
        if (cidadeDto.getId() == null) {
            throw new DadosMeteorologicosNaoInformadosException("ID do dado metereológico não foi informado");
        }
        Optional<Cidade> previsao = cidadeRepository.findById(cidadeDto.getId());
        if (previsao.isPresent()) {
            Cidade cidade = previsao.get();
            // Atualize os campos da cidade com base nos campos do DTO
            cidade.setNomeCidade(cidadeDto.getNomeCidade());
            cidade.setCidadeTurno(cidadeDto.getCidadeTurno());
            cidade.setCidadeTempo(cidadeDto.getCidadeTempo());
            cidade.setTemperaturaMaxima(cidadeDto.getTemperaturaMaxima());
            cidade.setTemperaturaMinima(cidadeDto.getTemperaturaMinima());
            cidade.setPrecipitacao(cidadeDto.getPrecipitacao());
            cidade.setHumidade(cidadeDto.getHumidade());
            cidade.setVelocidadeDoVento(cidadeDto.getVelocidadeDoVento());

            return cidadeMapper.toDto(cidadeRepository.save(cidade));
        } else {
            throw new DadosMeteorologicosNaoInformadosException("Dado metereológico não encontrado para o ID fornecido");

        }
    }

    @Override
    public void excluirDadosMeteorologicos(Long id) throws DadosMeteorologicosNaoInformadosException {
        Optional<Cidade> cidadeOptional = cidadeRepository.findById(id);
        if (cidadeOptional.isPresent()) {
            Cidade cidade = cidadeOptional.get();
            cidadeRepository.delete(cidade);
        } else {
            throw new DadosMeteorologicosNaoInformadosException("Não encontramos dados com o ID");
        }
    }


}

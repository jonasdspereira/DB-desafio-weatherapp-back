package com.app.weather.services.impl;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.exceptions.DadosMeteorologicosException;
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

    @Override
    public CidadeDto salvarDadosMeteorologicos(CidadeDto cidadeDto) throws DadosMeteorologicosException {
        try {
            Cidade cidade = CidadeMapper.MAPPER.toEntity(cidadeDto);
            Cidade cidadeSalva = cidadeRepository.save(cidade);
            return cidadeMapper.MAPPER.toDto(cidadeSalva);
        } catch (Exception e) {
            throw new DadosMeteorologicosException("Erro ao salvar dados meteorológicos: " + e.getMessage(), e);
        }
    }


    @Override
    public List<CidadeDto> buscarPrevisaoAtual(String nomeCidade) throws DadosMeteorologicosNaoInformadosException {
        try {
            LocalDateTime dataAtual = LocalDateTime.of(2024, Month.MAY, 9, 00, 00, 00);

            List<Cidade> previsao = cidadeRepository.findByDataCadastroAndNomeCidade(dataAtual, nomeCidade);

            List<CidadeDto> previsaoDto = previsao.stream().map(cidadeMapper::toDto).collect(Collectors.toList());
            return previsaoDto;
        } catch (Exception e) {
            throw new DadosMeteorologicosNaoInformadosException("Erro ao buscar previsão atual: " + e.getMessage());
        }
    }

    @Override
    public List<CidadeDto> buscarPrevisao7Dias(String nomeCidade) throws DadosMeteorologicosNaoInformadosException {
        try {
            LocalDateTime hoje = LocalDateTime.now();
            LocalDateTime umaSemana = hoje.plusDays(7);

            List<Cidade> previsoes = cidadeRepository.findByNomeCidadeAndDataCadastroBetween(nomeCidade, hoje, umaSemana);

            if (!previsoes.isEmpty()) {
                return previsoes.stream()
                        .map(cidadeMapper::toDto)
                        .collect(Collectors.toList());
            } else {
                throw new DadosMeteorologicosNaoInformadosException("Não foram encontradas previsões para os próximos dias para a cidade: " + nomeCidade);
            }
        } catch (Exception e) {

            throw new DadosMeteorologicosNaoInformadosException("Erro ao buscar previsão para os próximos dias: " + e.getMessage());
        }
    }


    @Override
    public CidadeDto alterarDadosMeteorologicos(CidadeDto cidadeDto) throws DadosMeteorologicosNaoInformadosException {
        try {

            if (cidadeDto.getId() == null) {
                throw new DadosMeteorologicosNaoInformadosException("ID do dado meteorológico não foi informado");
            }


            Optional<Cidade> previsao = cidadeRepository.findById(cidadeDto.getId());


            if (previsao.isPresent()) {
                Cidade cidade = previsao.get();

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

                throw new DadosMeteorologicosNaoInformadosException("Dado meteorológico não encontrado para o ID fornecido");
            }
        } catch (DadosMeteorologicosNaoInformadosException e) {

            throw new DadosMeteorologicosNaoInformadosException("Erro ao alterar dados meteorológicos: " + e.getMessage());
        }
    }


    @Override
    public void excluirDadosMeteorologicos(Long id) throws DadosMeteorologicosNaoInformadosException {
        try {
            Optional<Cidade> cidadeOptional = cidadeRepository.findById(id);

            if (cidadeOptional.isPresent()) {
                Cidade cidade = cidadeOptional.get();
                cidadeRepository.delete(cidade);
            } else {
                throw new DadosMeteorologicosNaoInformadosException("Não encontramos dados com o ID: " + id);
            }
        } catch (Exception e) {
            throw new DadosMeteorologicosNaoInformadosException("Erro ao excluir dados meteorológicos: " + e.getMessage());
        }
    }


}

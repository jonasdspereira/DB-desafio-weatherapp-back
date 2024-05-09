package com.app.weather.controllers;


import com.app.weather.dto.CidadeDto;
import com.app.weather.exceptions.CidadeNaoEncontradaException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.services.impl.CidadeServiceImpl;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/previsao")
public class CidadeController {

    @Autowired
    private CidadeServiceImpl cidadeService;

    @Autowired
    private CidadeMapper cidadeMapper;



    @Transactional
    @PostMapping
    public ResponseEntity<CidadeDto> salvarDadosMeteorologicos(@RequestBody CidadeDto dto) throws CidadeNaoEncontradaException {
        CidadeDto cidadeDto = cidadeService.salvarDadosMeteorologicos(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeDto);
    }

    @GetMapping("/{nomeCidade}")
    public ResponseEntity<List<CidadeDto>> buscarPrevisaoAtual(@PathVariable("nomeCidade") String nomeCidade)  throws DadosMeteorologicosNaoInformadosException {
        nomeCidade = nomeCidade.replace("-", " ");
        List<CidadeDto> cidadesDto = cidadeService.buscarPrevisaoAtual(nomeCidade);
        if (!cidadesDto.isEmpty()) {
            return ResponseEntity.ok(cidadesDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{nomeCidade}/proximosdias")
    public ResponseEntity<List<CidadeDto>> buscarPrevisao7Dias() throws DadosMeteorologicosNaoInformadosException {
        List<CidadeDto> previsoes = cidadeService.buscarPrevisao7Dias();
            return ResponseEntity.ok(previsoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CidadeDto> alterarDadosMeteorologicos(@PathVariable Long id, @RequestBody CidadeDto dto) throws DadosMeteorologicosNaoInformadosException {
        dto.getId();

        if (dto == null) {
            throw new DadosMeteorologicosNaoInformadosException("ID do dado metereológico não foi informado");
        }

        if (!id.equals(dto.getId())) {
            throw new DadosMeteorologicosNaoInformadosException("O ID dos dados meteorológicos não corresponde ao ID fornecido na URL");
        }
        CidadeDto previsaoAtualizada = cidadeService.alterarDadosMeteorologicos(dto);
        return ResponseEntity.ok(previsaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDadosMeteorologicos(@PathVariable Long id) throws DadosMeteorologicosNaoInformadosException {
        cidadeService.excluirDadosMeteorologicos(id);
        return ResponseEntity.noContent().build();
    }



}

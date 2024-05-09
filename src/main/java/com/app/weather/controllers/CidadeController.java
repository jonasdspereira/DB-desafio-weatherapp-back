package com.app.weather.controllers;


import com.app.weather.dto.CidadeDto;
import com.app.weather.exceptions.DadosMeteorologicosException;
import com.app.weather.exceptions.DadosMeteorologicosNaoInformadosException;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.services.impl.CidadeServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/previsao")
public class CidadeController {

    @Autowired
    private CidadeServiceImpl cidadeService;

    @Autowired
    private CidadeMapper cidadeMapper;


    @Transactional
    @PostMapping
    public ResponseEntity<Object> salvarDadosMeteorologicos(@RequestBody CidadeDto dto) {
        try {
            CidadeDto cidadeDto = cidadeService.salvarDadosMeteorologicos(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeDto);
        } catch (DadosMeteorologicosException e) {
            String errorMessage = "Erro ao salvar dados meteorológicos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/{nomeCidade}")
    public ResponseEntity<List<CidadeDto>> buscarPrevisaoAtual(@PathVariable("nomeCidade") String nomeCidade) {
        try {
            nomeCidade = nomeCidade.replace("-", " ");
            List<CidadeDto> cidadesDto = cidadeService.buscarPrevisaoAtual(nomeCidade);

            if (!cidadesDto.isEmpty()) {
                return ResponseEntity.ok(cidadesDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DadosMeteorologicosNaoInformadosException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{nomeCidade}/proximosdias")
    public ResponseEntity<List<CidadeDto>> buscarPrevisao7Dias(@PathVariable("nomeCidade") String nomeCidade) {
        try {
            nomeCidade = nomeCidade.replace("-", " ");
            List<CidadeDto> previsoes = cidadeService.buscarPrevisao7Dias(nomeCidade);
            if (!previsoes.isEmpty()) {
                return ResponseEntity.ok(previsoes);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (DadosMeteorologicosNaoInformadosException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarDadosMeteorologicos(@PathVariable Long id, @RequestBody CidadeDto dto) {
        try {

            if (dto == null) {
                throw new DadosMeteorologicosNaoInformadosException("ID do dado meteorológico não foi informado");
            }


            if (!id.equals(dto.getId())) {
                throw new DadosMeteorologicosNaoInformadosException("O ID dos dados meteorológicos não corresponde ao ID fornecido na URL");
            }


            CidadeDto previsaoAtualizada = cidadeService.alterarDadosMeteorologicos(dto);
            return ResponseEntity.ok(previsaoAtualizada);
        } catch (DadosMeteorologicosNaoInformadosException e) {

            String errorMessage = "Erro ao atualizar dados meteorológicos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorMessage);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirDadosMeteorologicos(@PathVariable Long id) {
        try {

            cidadeService.excluirDadosMeteorologicos(id);
            return ResponseEntity.noContent().build();
        } catch (DadosMeteorologicosNaoInformadosException e) {
            String errorMessage = "Erro ao excluir dados meteorológicos: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }
    }


}

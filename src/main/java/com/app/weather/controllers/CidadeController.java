package com.app.weather.controllers;


import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import com.app.weather.exceptions.CidadeNaoEncontradaException;
import com.app.weather.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cadastro")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<CidadeDto> salvarCidade(@RequestBody CidadeDto dto) {
        CidadeDto cidade = cidadeService.salvarCidade(dto);
        return new ResponseEntity<>(cidade, HttpStatus.CREATED);
    }

    @GetMapping("/{nomeCidade}")
    public ResponseEntity<List<CidadeDto>> buscarCidade(@PathVariable("nomeCidade") String nomeCidade) {
        nomeCidade = nomeCidade.replace("-", " ");
        List<CidadeDto> cidadesDto = cidadeService.buscarCidade(nomeCidade);
        if (!cidadesDto.isEmpty()) {
            return ResponseEntity.ok(cidadesDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

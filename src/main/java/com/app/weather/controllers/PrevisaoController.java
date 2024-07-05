package com.app.weather.controllers;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.services.impl.PrevisaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
public class PrevisaoController implements PrevisaoSwagger {

    private final PrevisaoServiceImpl previsaoService;

    @Autowired
    public PrevisaoController(PrevisaoServiceImpl cidadeService) {
        this.previsaoService = cidadeService;
    }

    @Override
    public ResponseEntity<PrevisaoDto> salvarDadosMeteorologicos(@Valid PrevisaoDto dto) {
        PrevisaoDto previsaoDto = previsaoService.salvarDadosMeteorologicos(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(previsaoDto);
    }

    @Override
    public ResponseEntity<List<PrevisaoDto>> buscarPrevisoes() {
        List<PrevisaoDto> previsoes = previsaoService.buscarPrevisoes();
        return ResponseEntity.ok().body(previsoes);
    }

    @Override
    public ResponseEntity<List<PrevisaoDto>> buscarPrevisaoPeloNome(@Valid String nomeCidade) {
        List<PrevisaoDto> previsoes = previsaoService.buscarPrevisaoPeloNome(nomeCidade);
        return ResponseEntity.ok().body(previsoes);
    }

    @Override
    public ResponseEntity<PrevisaoDto> buscarPrevisao(@Valid Long id) {
        PrevisaoDto previsoes = previsaoService.buscarPrevisao(id);
        return ResponseEntity.ok().body(previsoes);
    }

    @Override
    public ResponseEntity<PrevisaoDto> alterarDadosMeteorologicos(@Valid Long id, @Valid PrevisaoDto dto) {
        PrevisaoDto previsaoAtualizada = previsaoService.alterarDadosMeteorologicos(id, dto);
        return ResponseEntity.ok(previsaoAtualizada);
    }

    @Override
    public ResponseEntity<Void> excluirDadosMeteorologicos(@Valid Long id) {
        previsaoService.excluirDadosMeteorologicos(id);
        return ResponseEntity.noContent().build();
    }
}

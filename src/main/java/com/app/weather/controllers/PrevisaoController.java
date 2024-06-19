package com.app.weather.controllers;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.services.impl.PrevisaoServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/previsao")
@CrossOrigin("http://localhost:5173")
public class PrevisaoController {

    private final PrevisaoServiceImpl previsaoService;

    @Autowired
    public PrevisaoController(PrevisaoServiceImpl cidadeService) {
        this.previsaoService = cidadeService;
    }

    @PostMapping
    public ResponseEntity<PrevisaoDto> salvarDadosMeteorologicos(@RequestBody PrevisaoDto dto) {
        PrevisaoDto previsaoDto = previsaoService.salvarDadosMeteorologicos(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(previsaoDto);
    }

    @GetMapping("/{nomeCidade}/all")
    public ResponseEntity<List<PrevisaoDto>> buscarPrevisoes(@PathVariable("nomeCidade") @Valid @NotNull String nomeCidade) {
        List<PrevisaoDto> previsoes = previsaoService.buscarPrevisoes(nomeCidade);
        return ResponseEntity.ok().body(previsoes);
    }

    @GetMapping("/{nomeCidade}")
    public ResponseEntity<PrevisaoDto> buscarPrevisaoAtual(@PathVariable("nomeCidade") @Valid @NotNull String nomeCidade) {
        PrevisaoDto previsao = previsaoService.buscarPrevisaoAtual(nomeCidade);
        return ResponseEntity.ok().body(previsao);
    }

    @GetMapping("/{nomeCidade}/proximosdias")
    public ResponseEntity<List<PrevisaoDto>> buscarPrevisao7Dias(@PathVariable("nomeCidade") @Valid @NotNull String nomeCidade) {
        List<PrevisaoDto> previsoes = previsaoService.buscarPrevisao7Dias(nomeCidade);
        return ResponseEntity.ok().body(previsoes);
    }

    @PutMapping("/{nomeCidade}/{id}")
    public ResponseEntity<PrevisaoDto> alterarDadosMeteorologicos(@PathVariable Long id, @Valid @NotNull @RequestBody PrevisaoDto dto) {
        PrevisaoDto previsaoAtualizada = previsaoService.alterarDadosMeteorologicos(id, dto);
        return ResponseEntity.ok(previsaoAtualizada);
    }

    @DeleteMapping("/{nomeCidade}/{id}")
    public ResponseEntity<Void> excluirDadosMeteorologicos(@PathVariable Long id) {
        previsaoService.excluirDadosMeteorologicos(id);
        return ResponseEntity.noContent().build();
    }
}

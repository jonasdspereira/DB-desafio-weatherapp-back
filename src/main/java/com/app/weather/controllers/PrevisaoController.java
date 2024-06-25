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
@RequestMapping("/previsoes")
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

    @GetMapping("/todas")
    public ResponseEntity<List<PrevisaoDto>> buscarPrevisoes() {
        List<PrevisaoDto> previsoes = previsaoService.buscarPrevisoes();
        return ResponseEntity.ok().body(previsoes);
    }

    @GetMapping("/{nomeCidade}")
    public ResponseEntity<List<PrevisaoDto>> buscarPrevisaoPeloNome(@Valid @NotNull @PathVariable String nomeCidade) {
        List<PrevisaoDto> previsoes = previsaoService.buscarPrevisaoPeloNome(nomeCidade);
        return ResponseEntity.ok().body(previsoes);
    }

    @GetMapping("/previsao/{id}")
    public ResponseEntity<List<PrevisaoDto>> buscarPrevisao(@Valid @NotNull @PathVariable Long id) {
        List<PrevisaoDto> previsoes = previsaoService.buscarPrevisao(id);
        return ResponseEntity.ok().body(previsoes);
    }


    @PutMapping("/{nomeCidade}/{id}")
    public ResponseEntity<PrevisaoDto> alterarDadosMeteorologicos(@PathVariable Long id, @Valid @NotNull @RequestBody PrevisaoDto dto) {
        PrevisaoDto previsaoAtualizada = previsaoService.alterarDadosMeteorologicos(id, dto);
        return ResponseEntity.ok(previsaoAtualizada);
    }

    @DeleteMapping("/previsao/{id}")
    public ResponseEntity<Void> excluirDadosMeteorologicos(@PathVariable Long id) {
        previsaoService.excluirDadosMeteorologicos(id);
        return ResponseEntity.noContent().build();
    }
}

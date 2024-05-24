package com.app.weather.controllers;

import com.app.weather.dto.CidadeDto;
import com.app.weather.services.impl.CidadeServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/previsao")
public class CidadeController {

    private final CidadeServiceImpl cidadeService;

    @Autowired
    public CidadeController(CidadeServiceImpl cidadeService) {
        this.cidadeService = cidadeService;
    }

    @PostMapping
    public ResponseEntity<CidadeDto> salvarDadosMeteorologicos(@RequestBody CidadeDto dto) {
        CidadeDto cidadeDto = cidadeService.salvarDadosMeteorologicos(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeDto);
    }

    @GetMapping("/{nomeCidade}")
    public ResponseEntity<CidadeDto> buscarPrevisaoAtual(@PathVariable("nomeCidade") @Valid @NotNull String nomeCidade) {
        CidadeDto previsao = cidadeService.buscarPrevisaoAtual(nomeCidade);
        return ResponseEntity.ok().body(previsao);
    }

    @GetMapping("/{nomeCidade}/proximosdias")
    public ResponseEntity<List<CidadeDto>> buscarPrevisao7Dias(@PathVariable("nomeCidade") @Valid @NotNull String nomeCidade) {
        List<CidadeDto> previsoes = cidadeService.buscarPrevisao7Dias(nomeCidade);
        return ResponseEntity.ok().body(previsoes);
    }

    @PutMapping("/{nomeCidade}/{id}")
    public ResponseEntity<CidadeDto> alterarDadosMeteorologicos(@PathVariable Long id, @Valid @NotNull @RequestBody CidadeDto dto) {
        CidadeDto previsaoAtualizada = cidadeService.alterarDadosMeteorologicos(id, dto);
        return ResponseEntity.ok(previsaoAtualizada);
    }

    @DeleteMapping("/{nomeCidade}/{id}")
    public ResponseEntity<Void> excluirDadosMeteorologicos(@PathVariable Long id) {
        cidadeService.excluirDadosMeteorologicos(id);
        return ResponseEntity.noContent().build();
    }
}

package com.app.weather.controllers;

import com.app.weather.dto.PrevisaoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RequestMapping("/previsoes")
@CrossOrigin("http://localhost:5173")
public interface PrevisaoSwagger {

    @Operation(
            operationId = "salvarDadosMeteorologicos",
            summary = "Salvar dados meteorológicos",
            description = "Endpoint para salvar dados meteorológicos de uma cidade",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Dados meteorológicos salvos com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PrevisaoDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida"
                    )
            }
    )
    @PostMapping
    ResponseEntity<PrevisaoDto> salvarDadosMeteorologicos(@Valid @RequestBody PrevisaoDto dto);

    @Operation(
            operationId = "buscarTodasPrevisoes",
            summary = "Buscar todas as previsões",
            description = "Endpoint para buscar todas as previsões meteorológicas cadastradas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Previsões encontradas",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = PrevisaoDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cidade não encontrada"
                    )
            }
    )
    @GetMapping("/todas")
    ResponseEntity<List<PrevisaoDto>> buscarPrevisoes();

    @Operation(
            operationId = "buscarPrevisaoPorNome",
            summary = "Buscar previsão pelo nome da cidade",
            description = "Endpoint para buscar previsões meteorológicas por nome de cidade",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Previsões encontradas",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = PrevisaoDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cidade não encontrada"
                    )
            }
    )
    @GetMapping("/{nomeCidade}")
    ResponseEntity<List<PrevisaoDto>> buscarPrevisaoPeloNome(@Valid @NotNull @PathVariable("nomeCidade") String nomeCidade);

    @Operation(
            operationId = "buscarPrevisaoPorId",
            summary = "Buscar previsão pelo ID",
            description = "Endpoint para buscar previsão meteorológica por ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Previsão encontrada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PrevisaoDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Previsão não encontrada"
                    )
            }
    )
    @GetMapping("/previsao/{id}")
    ResponseEntity<PrevisaoDto> buscarPrevisao(@Valid @NotNull @PathVariable("id") Long id);

    @Operation(
            operationId = "alterarDadosMeteorologicos",
            summary = "Alterar dados meteorológicos",
            description = "Endpoint para alterar dados meteorológicos de uma previsão existente",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dados meteorológicos alterados com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PrevisaoDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Previsão não encontrada"
                    )
            }
    )
    @PutMapping("/previsao/{id}")
    ResponseEntity<PrevisaoDto> alterarDadosMeteorologicos(@Valid @NotNull @PathVariable("id") Long id, @Valid @RequestBody PrevisaoDto dto);

    @Operation(
            operationId = "excluirDadosMeteorologicos",
            summary = "Excluir dados meteorológicos",
            description = "Endpoint para excluir dados meteorológicos de uma previsão existente",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Dados meteorológicos excluídos com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Previsão não encontrada"
                    )
            }
    )
    @DeleteMapping("/previsao/{id}")
    ResponseEntity<Void> excluirDadosMeteorologicos(@Valid @NotNull @PathVariable("id") Long id);

}

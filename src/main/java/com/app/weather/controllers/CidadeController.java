package com.app.weather.controllers;


import com.app.weather.dto.request.CidadeRequestDto;
import com.app.weather.mapper.CidadeMapper;
import com.app.weather.services.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cadastro")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<CidadeRequestDto> salvarCidade(@RequestBody CidadeRequestDto dto) {
        CidadeRequestDto cidade = cidadeService.salvarCidade(dto);
        return new ResponseEntity<>(cidade, HttpStatus.CREATED);
    }
}

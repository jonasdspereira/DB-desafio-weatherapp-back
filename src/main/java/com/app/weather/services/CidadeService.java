package com.app.weather.services;

import com.app.weather.dto.request.CidadeRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface CidadeService {

    CidadeRequestDto salvarCidade (CidadeRequestDto cidadeRequestDto);

}



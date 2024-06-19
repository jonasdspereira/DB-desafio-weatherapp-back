package com.app.weather.mapper;

import com.app.weather.dto.PrevisaoDto;
import com.app.weather.entities.Previsao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrevisaoMapper {
    PrevisaoDto toDto(Previsao previsao);
    Previsao toEntity(PrevisaoDto previsaoDto);
}

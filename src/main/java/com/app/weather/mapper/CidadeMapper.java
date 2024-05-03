package com.app.weather.mapper;

import com.app.weather.dto.request.CidadeRequestDto;
import com.app.weather.entities.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CidadeMapper {
    CidadeMapper MAPPER = Mappers.getMapper(CidadeMapper.class);

    CidadeRequestDto toDto (Cidade cidade);

    Cidade toEntity (CidadeRequestDto cidadeRequestDto);

}

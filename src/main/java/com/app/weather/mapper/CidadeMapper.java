package com.app.weather.mapper;

import com.app.weather.dto.CidadeDto;
import com.app.weather.entities.Cidade;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CidadeMapper {
    CidadeMapper MAPPER = Mappers.getMapper(CidadeMapper.class);

    CidadeDto toDto (Cidade cidade);

    Cidade toEntity (CidadeDto cidadeDto);

}

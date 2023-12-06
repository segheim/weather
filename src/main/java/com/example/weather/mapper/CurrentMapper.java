package com.example.weather.mapper;

import com.example.weather.dto.CurrentDto;
import com.example.weather.model.Current;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrentMapper {

    CurrentDto toDto(Current current);

}

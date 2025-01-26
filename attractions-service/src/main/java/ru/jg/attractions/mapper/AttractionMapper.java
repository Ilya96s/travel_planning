package ru.jg.attractions.mapper;

import org.mapstruct.Mapper;
import ru.jg.attractions.model.Attraction;
import ru.jg.attractions.payload.AttractionResponseDto;

@Mapper(componentModel = "spring")
public interface AttractionMapper {

    AttractionResponseDto fromEntityToDto(Attraction attraction);
}

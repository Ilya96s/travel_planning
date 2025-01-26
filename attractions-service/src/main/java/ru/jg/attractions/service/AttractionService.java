package ru.jg.attractions.service;

import ru.jg.attractions.payload.AttractionResponseDto;

public interface AttractionService {

    public AttractionResponseDto getAttraction(Long id);

}

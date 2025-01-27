package ru.jg.attractions.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.jg.attractions.exception.AttractionNotFoundException;
import ru.jg.attractions.mapper.AttractionMapper;
import ru.jg.attractions.model.Attraction;
import ru.jg.attractions.payload.AttractionResponseDto;
import ru.jg.attractions.repository.AttractionRepository;
import ru.jg.attractions.service.AttractionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;

    private final AttractionRepository attractionRepository;

    private static Map<Long, Attraction> attractions = new HashMap<>();

    static {
        attractions.put(11L, new Attraction(11L,
                "Эйфелева башня",
                "Париж",
                "Знаменитая башня высотой 330 метров, символ Франции.")
        );
    }

    public AttractionResponseDto getAttraction(Long id) {
        Optional<Attraction> attraction = attractionRepository.findById(id);
        if (attraction.isPresent()) {
            log.info("Attraction with id {} found", id);
            return attractionMapper.fromEntityToDto(attraction.get());
        } else {
            log.info("Attraction with id {} not found", id);
            throw new AttractionNotFoundException("Attraction with id " + id + " not found");
        }
    }
}

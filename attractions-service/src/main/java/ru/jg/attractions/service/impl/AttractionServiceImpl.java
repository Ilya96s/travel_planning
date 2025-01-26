package ru.jg.attractions.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.jg.attractions.mapper.AttractionMapper;
import ru.jg.attractions.model.Attraction;
import ru.jg.attractions.payload.AttractionResponseDto;
import ru.jg.attractions.service.AttractionService;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionMapper attractionMapper;

    private static Map<Long, Attraction> attractions = new HashMap<>();

    static {
        attractions.put(1L, new Attraction(1L,
                "Эйфелева башня",
                "Париж",
                "Знаменитая башня высотой 330 метров, символ Франции.")
        );
    }

    public AttractionResponseDto getAttraction(Long id) {
        if (attractions.containsKey(id)) {
            log.info("Attraction with id {} found", id);
            return attractionMapper.fromEntityToDto(attractions.get(id));
        } else {
            log.info("Attraction with id {} not found", id);
            throw new IllegalArgumentException("Attraction with id " + id + " not found");
        }
    }
}

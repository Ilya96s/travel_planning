package ru.jg.attractions.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jg.attractions.payload.AttractionResponseDto;
import ru.jg.attractions.service.impl.AttractionServiceImpl;

@RestController
@RequestMapping("/api/v1/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionServiceImpl attractionServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<AttractionResponseDto> getAttraction(@PathVariable("id") Long id) {
        AttractionResponseDto attraction = attractionServiceImpl.getAttraction(id);
        return ResponseEntity.ok(attraction);
    }
}

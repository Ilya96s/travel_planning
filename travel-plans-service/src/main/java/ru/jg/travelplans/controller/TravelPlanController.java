package ru.jg.travelplans.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jg.travelplans.dto.TravelPlanResponseDto;
import ru.jg.travelplans.service.TravelPlanService;

@RestController
@RequestMapping("api/v1/plans")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlanResponseDto> getTravelPlanById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(travelPlanService.findTravelPlanById(id));
    }
}

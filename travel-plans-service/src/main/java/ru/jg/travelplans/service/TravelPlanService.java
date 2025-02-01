package ru.jg.travelplans.service;

import ru.jg.travelplans.dto.TravelPlanResponseDto;

public interface TravelPlanService {

    TravelPlanResponseDto findTravelPlanById(Long id);
}

package ru.jg.travelplans.service;

import ru.jg.travelplans.payload.TravelPlanResponseDto;

public interface TravelPlanService {

    TravelPlanResponseDto findTravelPlanById(Long id);
}

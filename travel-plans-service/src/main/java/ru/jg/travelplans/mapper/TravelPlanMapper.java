package ru.jg.travelplans.mapper;

import org.mapstruct.Mapper;
import ru.jg.travelplans.model.TravelPlan;
import ru.jg.travelplans.payload.TravelPlanResponseDto;

@Mapper(componentModel = "spring")
public interface TravelPlanMapper {

    TravelPlanResponseDto fromEntityToDto(TravelPlan travelPlan);
}

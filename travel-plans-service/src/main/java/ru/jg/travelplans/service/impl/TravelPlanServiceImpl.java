package ru.jg.travelplans.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jg.travelplans.client.AttractionOpenFeignClient;
import ru.jg.travelplans.dto.AttractionResponseDto;
import ru.jg.travelplans.dto.TravelPlanResponseDto;
import ru.jg.travelplans.exception.TravelPlanNotFoundException;
import ru.jg.travelplans.mapper.TravelPlanMapper;
import ru.jg.travelplans.model.TravelPlan;
import ru.jg.travelplans.repository.TravelPlanRepository;
import ru.jg.travelplans.service.TravelPlanService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TravelPlanServiceImpl implements TravelPlanService {

    private final TravelPlanMapper travelPlanMapper;

    private final AttractionOpenFeignClient attractionOpenFeignClient;

    private final TravelPlanRepository travelPlanRepository;

    @Override
    public TravelPlanResponseDto findTravelPlanById(Long id) {
        Optional<TravelPlan> travelPlanOptional = travelPlanRepository.findById(id);
        if (travelPlanOptional.isEmpty()) {
            throw new TravelPlanNotFoundException("Travel plan with id " + id + " not found");
        }

        TravelPlan travelPlan = travelPlanOptional.get();
        TravelPlanResponseDto travelPlanResponseDto = travelPlanMapper.fromEntityToDto(travelPlan);

        AttractionResponseDto attractionResponseDto = attractionOpenFeignClient.getAttractionById(
                travelPlan.getAttractionId()
        );

        travelPlanResponseDto.setAttraction(attractionResponseDto);

        return travelPlanResponseDto;
    }
}

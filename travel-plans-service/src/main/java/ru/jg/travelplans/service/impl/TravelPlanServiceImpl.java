package ru.jg.travelplans.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.jg.travelplans.client.AttractionOpenFeignClient;
import ru.jg.travelplans.dto.AttractionResponseDto;
import ru.jg.travelplans.dto.TravelPlanResponseDto;
import ru.jg.travelplans.event.AttractionCreatedEvent;
import ru.jg.travelplans.exception.TravelPlanNotFoundException;
import ru.jg.travelplans.mapper.TravelPlanMapper;
import ru.jg.travelplans.model.TravelPlan;
import ru.jg.travelplans.repository.TravelPlanRepository;
import ru.jg.travelplans.service.TravelPlanService;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravelPlanServiceImpl implements TravelPlanService {

    private final TravelPlanMapper travelPlanMapper;

    private final AttractionOpenFeignClient attractionOpenFeignClient;

    private final TravelPlanRepository travelPlanRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${travel-plan.request-topic}")
    private String travelPlanRequestTopic;

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

    @Override
    public void createNewPlan(TravelPlanResponseDto travelPlanResponseDto) {
        //TODO save plan to db

        AttractionCreatedEvent attractionCreatedEvent =
                new AttractionCreatedEvent(travelPlanResponseDto.getAttraction().name(),
                        travelPlanResponseDto.getAttraction().city(),
                        travelPlanResponseDto.getAttraction().description());

        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(travelPlanRequestTopic, attractionCreatedEvent);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to send message: {}", exception.getMessage());
            } else {
                log.info("Message sent successfully: {}", result.getRecordMetadata());
                log.info("Topic: {}", result.getRecordMetadata().topic());
                log.info("Partition: {}", result.getRecordMetadata().partition());
                log.info("Offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}

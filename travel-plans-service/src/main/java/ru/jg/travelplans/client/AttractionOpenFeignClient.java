package ru.jg.travelplans.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.jg.travelplans.dto.AttractionResponseDto;

@FeignClient(name = "attraction-service")
public interface AttractionOpenFeignClient {

    @GetMapping("/api/v1/attractions/{attractionId}")
    AttractionResponseDto getAttractionById(@PathVariable("attractionId") Long attractionId);

}

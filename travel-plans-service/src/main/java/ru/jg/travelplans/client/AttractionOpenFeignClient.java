package ru.jg.travelplans.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.jg.travelplans.dto.AttractionResponseDto;

@FeignClient(name = "api-gateway")
public interface AttractionOpenFeignClient {

    @GetMapping("/api/v1/attractions/{attractionId}")
    @Retry(name = "attractionCB", fallbackMethod = "getAttractionByIdFallbackMethod")
    @CircuitBreaker(name = "attractionCB", fallbackMethod = "getAttractionByIdFallbackMethod")
    AttractionResponseDto getAttractionById(@PathVariable("attractionId") Long attractionId);

    default AttractionResponseDto getAttractionByIdFallbackMethod(Throwable throwable) {
        return new AttractionResponseDto(0L, "Temp name", "Temp city", "Temp description");
    }
}

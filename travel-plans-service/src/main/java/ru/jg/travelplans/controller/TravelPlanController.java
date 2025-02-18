package ru.jg.travelplans.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.jg.travelplans.dto.TravelPlanResponseDto;
import ru.jg.travelplans.service.TravelPlanService;

@RestController
@RequestMapping("api/v1/plans")
@RequiredArgsConstructor
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlanResponseDto> getTravelPlanById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(travelPlanService.findTravelPlanById(id));
    }


    @GetMapping("/token")
    public String token() {
        //После того как менеджер авторизации получит токен, объект OAuth2AuthorizeRequest будет его содержать в себе
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
                //Имя клиента которого нужно авторизовать
                .withClientRegistrationId("travel-plan-service-client")
                //когда указываем client - это значит что запрос идет от клиента и будет использован client credentials type для получения токена
                .principal("client")
                .build();

        var client = authorizedClientManager.authorize(request);
        return client.getAccessToken().getTokenValue();
    }

    @PostMapping
    public ResponseEntity<Void> createNewPlan(@RequestBody TravelPlanResponseDto travelPlanResponseDto) {
        travelPlanService.createNewPlan(travelPlanResponseDto);
        return ResponseEntity.ok().build();
    }
}

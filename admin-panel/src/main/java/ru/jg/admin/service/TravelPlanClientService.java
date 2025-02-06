package ru.jg.admin.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.jg.admin.dto.TravelPlanResponseDto;

@Service
public class TravelPlanClientService {

    private RestTemplate restTemplate;

    public TravelPlanClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TravelPlanResponseDto getTravelPlanById(Long id) {
        String url = "http://127.0.0.1:7070/api/v1/plans/" + id;

        System.out.println(getAccessToken());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<TravelPlanResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                TravelPlanResponseDto.class
        );

        return response.getBody();
    }

    public String getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
            return oidcUser.getIdToken().getTokenValue();
        }
        throw new IllegalStateException("User not authenticated");
    }
}

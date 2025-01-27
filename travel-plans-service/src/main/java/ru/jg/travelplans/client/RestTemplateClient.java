package ru.jg.travelplans.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.jg.travelplans.exception.AttractionNotFoundException;
import ru.jg.travelplans.payload.AttractionResponseDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestTemplateClient {

    private final RestTemplate restTemplate;

    @Value("${application.config.attraction-url}")
    private String attractionUrl;

    public AttractionResponseDto getAttractionById(Long id) {
        try {
            AttractionResponseDto attractionResponseDto = restTemplate.getForObject(
                    attractionUrl + "/{id}", AttractionResponseDto.class, id
            );
            log.info("attraction response: {}", attractionResponseDto);
            return attractionResponseDto;
            //Attraction service возвращает 404 если по id не найдено записи
        } catch (HttpClientErrorException.BadRequest e) {
            log.error("Attraction not found for id: {}", id, e);
            throw new AttractionNotFoundException(
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Attraction with id " + id + " not found"
            );
            //В случае ошибки сети, когда сервис attraction недоступен
        } catch (ResourceAccessException e) {
            log.error("Failed to connect to attraction service", e);
            throw new ResourceAccessException("Attraction service is unavailable. Please try again later.");
            //В случае ошибки преобразования ответа
        } catch (RestClientException e) {
            log.error("Failed to parse response from attraction service", e);
            throw new RestClientException("Invalid response received from attraction service");
        }
    }
}

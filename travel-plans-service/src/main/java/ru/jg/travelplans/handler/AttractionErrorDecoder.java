package ru.jg.travelplans.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.ResourceAccessException;
import ru.jg.travelplans.exception.AttractionNotFoundException;

import java.io.IOException;

/**
 * Кастомный декодер ошибок для клиента {@link ru.jg.travelplans.client.AttractionOpenFeignClient}
 */
@Slf4j
public class AttractionErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodName, Response response) {
        log.error("Feign error: {} - {}", methodName, response.status());
        int status = response.status();

         switch (status) {
            case 404 -> {
                try {
                    if (response.body() != null) {
                        String responseBody = new String(response.body().asInputStream().readAllBytes());
                        JsonNode jsonNode = objectMapper.readTree(responseBody);
                        String errorMessage = jsonNode.has("message") ? jsonNode.get("message").asText() : responseBody;

                        return new AttractionNotFoundException(errorMessage);
                    }
                } catch (IOException e) {
                    return new RuntimeException("Error processing Feign client response", e);
                }
            }
            case 503 -> throw new ResourceAccessException("Attraction service is unavailable. Please try again later");
        };

        return defaultDecoder.decode(methodName, response);
    }
}

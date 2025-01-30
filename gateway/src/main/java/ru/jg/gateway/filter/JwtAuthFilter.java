package ru.jg.gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    private final WebClient webClient;

    @Value("${auth.server.validate-token-url}")
    private String validateTokenUrl;

    public JwtAuthFilter(WebClient webClient) {
        super(Config.class);
        this.webClient = webClient;
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String token = extractToken(exchange);
            if (token == null) {
                return onError(exchange, "Missing Token");
            }

            return webClient.get()
                    .uri(validateTokenUrl)
                    .headers(headers -> headers.setBearerAuth(token))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(result -> {
                        if (Boolean.TRUE.equals(result)) {
                            return chain.filter(exchange);
                        }
                        return onError(exchange, "Invalid Token");
                    })
                    .onErrorResume(e -> onError(exchange, "Authorization server unavailable"));

        });
    }

    private String extractToken(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        String jsonError = "{\"error\": \"" + error + "\"}";

        return response.writeWith(Mono.just(response.bufferFactory().wrap(jsonError.getBytes())));
    }

    public static class Config {

    }
}

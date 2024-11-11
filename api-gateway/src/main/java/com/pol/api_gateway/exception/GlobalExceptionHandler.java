package com.pol.api_gateway.exception;

import com.pol.api_gateway.config.ObjectMapperConfig;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(-2)
public class GlobalExceptionHandler {

    private final ObjectMapperConfig objectMapperConfig;

    public GlobalExceptionHandler(ObjectMapperConfig objectMapperConfig) {
        this.objectMapperConfig = objectMapperConfig;
    }

    @ExceptionHandler(InvalidJwtException.class)
    public Mono<Void> handleInvalidJwtException(ServerWebExchange exchange, InvalidJwtException ex){
        return buildErrorResponse(exchange, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, String message, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);
        errorResponse.put("path", exchange.getRequest().getPath().value());

        try {
            byte[] bytes = objectMapperConfig.objectMapper().writeValueAsBytes(errorResponse);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
        } catch (Exception e) {
            // In case JSON serialization fails, fallback to a plain message
            byte[] fallbackBytes = ("{\"error\":\"Failed to process error response\"}").getBytes(StandardCharsets.UTF_8);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(fallbackBytes)));
        }
    }
}

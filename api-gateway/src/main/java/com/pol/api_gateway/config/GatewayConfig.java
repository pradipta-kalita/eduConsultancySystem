package com.pol.api_gateway.config;

import com.pol.api_gateway.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class GatewayConfig {

    private final List<String> ADMIN_ONLY = List.of("ADMIN");
    private final List<String> STUDENT_ONLY = List.of("STUDENT");
    private final List<String> PARENT_ONLY = List.of("PARENT");
    private final List<String> ALL_ROLES = List.of("STUDENT","PARENT","ADMIN");

    private final JwtService jwtService;

    public GatewayConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("auth_route", r->r.path("/auth/**")
                                .filters(f->f.addRequestHeader("gateway","working"))
                        .uri("lb://USER-SERVICE"))
                .route("swagger_ui",r->r.path("/swagger-ui/index.html")
                        .uri("lb://USER-SERVICE")
                )
                .route("user_profile",r->r.path("/profile")
                        .uri("lb://USER-SERVICE")
                )
                .route("blog_service",r->r.path("/blogs/**","/tags/**")
                        .uri("lb://BLOG-SERVICE")
                )
                .build();
    }

    private Mono<Void> jwtAuthFilter(ServerWebExchange exchange, GatewayFilterChain chain, List<String> requiredRoles){
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return authFailureResponse(exchange,
                    "Unauthorized: Token is missing or invalid",
                    HttpStatus.UNAUTHORIZED.value());
        }

        String token = authHeader.substring(7);
        if(jwtService.isTokenExpired(token)){
            return authFailureResponse(exchange,"JWT token is expired",HttpStatus.FORBIDDEN.value());
        }
        List<String> roles;
        try {
            roles = jwtService.extractRoles(token);
        } catch (RuntimeException e) {
            return authFailureResponse(exchange, e.getMessage(),HttpStatus.UNAUTHORIZED.value());
        }
        if((roles == null) || !hasRequiredRole(roles,requiredRoles)){
            return authFailureResponse(exchange,
                    "Forbidden: You do not have the required permission",
                    HttpStatus.FORBIDDEN.value());
        }
        return chain.filter(exchange);
    }

    private boolean hasRequiredRole(List<String> userRoles, List<String> requiredRoles){
        for(String requiredRole: requiredRoles){
            if(userRoles.contains(requiredRole)){
                return true;
            }
        }
        return false;
    }

    private Mono<Void> authFailureResponse(ServerWebExchange exchange, String message,Integer statusCode){
        exchange.getResponse().setRawStatusCode(statusCode);
        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(message.getBytes())));
    }
}

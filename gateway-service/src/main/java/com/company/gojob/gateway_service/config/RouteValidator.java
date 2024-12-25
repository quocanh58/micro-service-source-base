package com.company.gojob.gateway_service.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/actuator/info/**"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().matches(uri));
}

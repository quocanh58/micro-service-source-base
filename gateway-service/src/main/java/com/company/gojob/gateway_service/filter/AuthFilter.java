package com.company.gojob.gateway_service.filter;

import com.company.gojob.gateway_service.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final JwtUtil jwtUtil;

    public AuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();

            // Bypass AuthFilter for specific paths
            if (path.startsWith("/api/v1/auth/login") || path.startsWith("/api/v1/auth/register")) {
                return chain.filter(exchange);
            }

            try {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Thiếu thông tin Authorization");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (!authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("Cấu trúc Authorization không đúng");
                }

                String token = authHeader.substring(7);
                jwtUtil.validateToken(token);

                String username = jwtUtil.extractUsername(token);
                if (username == null || username.isEmpty()) {
                    throw new RuntimeException("Lỗi xác thực");
                }

                ServerHttpRequest request = exchange.getRequest().mutate()
                        .header("X-auth-username", username)
                        .build();

                return chain.filter(exchange.mutate().request(request).build());

            } catch (RuntimeException e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }

    private String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtUtil.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

package com.company.gojob.account_service.config;

import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .description("API cho phép quản lý tài khoản")
                        .version("1.0"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP) // Sử dụng HTTP
                                .scheme("bearer") // Sử dụng Bearer Authentication
                                .bearerFormat("JWT") // Format của token
                                .in(SecurityScheme.In.HEADER) // Thêm vào Header
                                .name("Authorization")) // Tên của Authorization header
                )
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
                        .addList("JWT")); // Yêu cầu phải có Bearer Token cho các API
    }
}

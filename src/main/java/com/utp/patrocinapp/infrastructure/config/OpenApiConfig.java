package com.utp.patrocinapp.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "Bearer Authentication";

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("PatrocinApp API")

                        .description("""
                                API REST para la gestión de patrocinio deportivo.

                                Arquitectura:
                                • DDD
                                • Arquitectura Hexagonal
                                • Spring Boot 3.5
                                • Spring Security 7
                                • JWT
                                • PostgreSQL
                                """)

                        .version("1.0.0")

                        .contact(new Contact()
                                .name("Equipo PatrocinApp")
                                .email("equipo@patrocinapp.com"))

                        .license(new License()
                                .name("Uso Académico")))

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(SECURITY_SCHEME_NAME))

                .components(
                        new io.swagger.v3.oas.models.Components()

                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,

                                        new SecurityScheme()

                                                .name("Authorization")

                                                .type(SecurityScheme.Type.HTTP)

                                                .scheme("bearer")

                                                .bearerFormat("JWT")));
    }

}
package com.utp.patrocinapp.infrastructure.config;

import com.utp.patrocinapp.infrastructure.security.JwtAuthenticationFilter;
import com.utp.patrocinapp.infrastructure.security.RestAccessDeniedHandler;
import com.utp.patrocinapp.infrastructure.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;
    private final RestAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(errors -> errors
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/**", "/api/usuarios/**", "/swagger-ui/**",
                                "/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**",
                                "/actuator/health", "/actuator/health/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/plantillas/**", "/api/deportistas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/contratos").hasRole("NEGOCIO")
                        .requestMatchers(HttpMethod.POST, "/api/metas/*/evidencias").hasRole("DEPORTISTA")
                        .requestMatchers(HttpMethod.PUT, "/api/metas/*/evidencia").hasRole("DEPORTISTA")
                        .requestMatchers(HttpMethod.POST, "/api/evidencias/*/aprobar",
                                "/api/evidencias/*/rechazar", "/api/metas/*/aprobar").hasRole("NEGOCIO")
                        .anyRequest().authenticated())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                        .referrerPolicy(Customizer.withDefaults()))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

package com.utp.patrocinapp.infrastructure.service;

import com.utp.patrocinapp.domain.service.PasswordEncoderPort;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    @Override
    public String encode(String rawPassword) {

        // Temporal.
        // Más adelante cambiaremos por BCrypt.

        return rawPassword;

    }

}
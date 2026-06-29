package com.utp.patrocinapp.infrastructure.service;

import com.utp.patrocinapp.domain.service.PasswordEncoderPort;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    @Override
    public String encode(String rawPassword) {

        // Temporal.
        return rawPassword;

    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {

        return rawPassword.equals(encodedPassword);

    }

}
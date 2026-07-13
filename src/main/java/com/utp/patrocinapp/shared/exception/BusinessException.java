package com.utp.patrocinapp.shared.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends ApiException {
    public BusinessException(String message) {
        super(HttpStatus.BAD_REQUEST, "REGLA_NEGOCIO", message);
    }

    public BusinessException(HttpStatus status, String code, String message) {
        super(status, code, message);
    }
}

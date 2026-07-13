package com.utp.patrocinapp.shared.response;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String code,
        String message,
        String path,
        List<String> details) {

    public static ApiErrorResponse of(int status, String code, String message, String path) {
        return new ApiErrorResponse(Instant.now(), status, code, message, path, List.of());
    }

    public static ApiErrorResponse of(int status, String code, String message,
                                      String path, List<String> details) {
        return new ApiErrorResponse(Instant.now(), status, code, message, path, List.copyOf(details));
    }
}

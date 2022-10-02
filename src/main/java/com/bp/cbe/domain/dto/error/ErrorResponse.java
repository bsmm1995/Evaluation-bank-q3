package com.bp.cbe.domain.dto.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ErrorResponse implements Serializable {
    private String message;
    private String path;
    private LocalDateTime date;
    private HttpStatus status;

    public ErrorResponse(String message, String path, HttpStatus status) {
        this.message = message;
        this.path = path;
        this.status = status;
        date = LocalDateTime.now();
    }

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        date = LocalDateTime.now();
    }
}

package com.restaurante.demo.exceptions.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDto {
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    private LocalDateTime timestamp;
    private String description;
    private String exception;
}
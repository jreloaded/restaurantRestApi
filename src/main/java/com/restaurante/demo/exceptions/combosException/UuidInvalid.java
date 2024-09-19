package com.restaurante.demo.exceptions.combosException;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UuidInvalid extends RuntimeException implements Serializable {
    //Serializar es convertir un objeto en una cadena de bytes
    /*private static final long serialVersionUID = 1L;*/
    private String code;
    //Serializacion y deserializacion - Formato especial que nosotros escogemos
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    private LocalDateTime timestamp;
    private String exception;
    private HttpStatus status;

    public UuidInvalid(String code, LocalDateTime timestamp, String exception, HttpStatus status, String message) {

        super(message);
        this.code = code;
        this.status = status;
        this.exception = exception;
        this.timestamp = timestamp;

    }
}

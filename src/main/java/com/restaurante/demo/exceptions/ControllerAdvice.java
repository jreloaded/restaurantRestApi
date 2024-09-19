package com.restaurante.demo.exceptions;

import com.restaurante.demo.exceptions.clientException.CreateException;
import com.restaurante.demo.exceptions.combosException.UuidInvalid;
import com.restaurante.demo.exceptions.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CreateException.class)
    public ResponseEntity<ErrorDto> exceptionHadler(CreateException excep){
        ErrorDto error = ErrorDto
                .builder()
                .code(excep.getCode())
                //New Date lo que hace es proporcionarnos la hora actual
                .timestamp(LocalDateTime.now())
                .description(excep.getMessage())
                .exception(excep.getException())
                .build();
        return new ResponseEntity<>(error, excep.getStatus());
    }


    //Creamos esta para poderlo manejar pero no lo llamamos en nigun momento
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> generalException(Exception excep){
        ErrorDto error = ErrorDto
                .builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                //New Date lo que hace es proporcionarnos la hora actual
                .timestamp(LocalDateTime.now())
                .description(excep.getMessage())
                .exception("excep.getException()")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //eSTA EXCEPTION ES PARA VALIDAR QUE TODOS LOS CAMPOS SEAN CORRECTOS
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> argumentException(MethodArgumentNotValidException excep){

        Map<String, String> errores = new HashMap<>();
        //de esta manera recuperamos todos los errores y los recorremos
        excep.getBindingResult().getAllErrors().forEach(error -> {
            String nombreCampo = ((FieldError)error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(nombreCampo, mensaje);
        });

        ErrorDto error = ErrorDto
                .builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                //New Date lo que hace es proporcionarnos la hora actual
                .timestamp(LocalDateTime.now())
                .description(errores.toString())
                .exception("Datos del cliente inválidos o incompletos")
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    //Creamos esta para poderlo manejar pero no lo llamamos en nigun momento
    @ExceptionHandler(UuidInvalid.class)
    public ResponseEntity<ErrorDto> generalException(UuidInvalid excep){
        ErrorDto error = ErrorDto
                .builder()
                .code(HttpStatus.BAD_REQUEST.toString())
                //New Date lo que hace es proporcionarnos la hora actual
                .timestamp(LocalDateTime.now())
                .description(excep.getMessage())
                .exception("Uuid invalido")
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }





}

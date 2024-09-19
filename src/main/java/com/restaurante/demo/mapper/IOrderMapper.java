package com.restaurante.demo.mapper;

import com.restaurante.demo.modelo.dtos.OrderResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IOrderMapper {
    OrderResponseDTO orderResponseDTO(ResponseEntity<?> responseEntity);
}

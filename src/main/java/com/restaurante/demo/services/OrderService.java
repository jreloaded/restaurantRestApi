package com.restaurante.demo.services;

import com.restaurante.demo.modelo.dtos.OrderDto;
import com.restaurante.demo.modelo.dtos.OrderResponseDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(OrderResponseDTO orderResponseDTO);
    OrderDto updateOrder(UUID uuid,@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp, OrderResponseDTO orderResponseDTO);

}

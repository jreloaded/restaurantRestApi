package com.restaurante.demo.controller.clientes;

import com.restaurante.demo.exceptions.clientException.CreateException;
import com.restaurante.demo.modelo.dtos.OrderDto;
import com.restaurante.demo.modelo.dtos.OrderResponseDTO;
import com.restaurante.demo.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderResponseDTO orderResponseDTO) {
        OrderDto createdOrder = orderService.createOrder(orderResponseDTO);
        return ResponseEntity.status(201).body(createdOrder);
    }



    @PatchMapping("/update/{uuid}/delivered/{timestamp}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable String uuid, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp, @Valid @RequestBody OrderResponseDTO orderResponseDTO) {
        if (isValidUuid(uuid)) {
            OrderDto updatedOrder = orderService.updateOrder(UUID.fromString(uuid), timestamp ,orderResponseDTO);
            return ResponseEntity.ok(updatedOrder);
        }throw new CreateException(
                "E-1001",
                LocalDateTime.now(),
                "Uuid invalido",
                HttpStatus.CONFLICT,
                "Uuid invalido ");
    }


    private boolean isValidUuid (String uuid){
        try{
            UUID comboUuid = UUID.fromString(uuid);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }




}
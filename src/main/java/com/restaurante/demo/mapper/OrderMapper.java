package com.restaurante.demo.mapper;

import com.restaurante.demo.modelo.dtos.OrderDto;
import com.restaurante.demo.modelo.dtos.OrderResponseDTO;
import com.restaurante.demo.repositories.entity.ClientEntity;
import com.restaurante.demo.repositories.entity.CombosEntity;
import com.restaurante.demo.repositories.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class OrderMapper {

    public OrderDto toOrderDto(OrderResponseDTO orderResponseDTO) {
        if (orderResponseDTO == null) {
            return null;
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setUuid(UUID.randomUUID().toString());
        orderDto.setComboUuid(orderResponseDTO.getComboUuid());
        orderDto.setDocument(orderResponseDTO.getDocument());
        orderDto.setCantidad(orderResponseDTO.getQuantity());
        orderDto.setInformacionAdicional(orderResponseDTO.getAdditionalInformation());
        orderDto.setEntregado(false);
        orderDto.setFechaHoraEntrega(null);
        orderDto.setFechaHoraPedido(LocalDateTime.now());
        orderDto.setSubTotalSinIva(calculateSubTotal(orderResponseDTO.getQuantity()));
        orderDto.setValorIva(calculateIva(orderDto.getSubTotalSinIva()));
        orderDto.setTotalIncluyendoIva(orderDto.getSubTotalSinIva() + orderDto.getValorIva());
        orderDto.setCliente("ClienteEjemplo");

        return orderDto;
    }

    private float calculateSubTotal(int quantity) {
        return quantity * 50.0f;
    }

    private float calculateIva(float subTotal) {
        return subTotal * 0.12f;
    }

    public OrderEntity toOrderEntity(OrderDto orderDto, CombosEntity combo, ClientEntity client) {
        if (orderDto == null) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCliente(client);
        orderEntity.setCombo(combo);
        orderEntity.setFechaHoraPedido(orderDto.getFechaHoraPedido());
        orderEntity.setCantidad(orderDto.getCantidad());
        orderEntity.setInformacionAdicional(orderDto.getInformacionAdicional());
        orderEntity.setSubTotalSinIva(orderDto.getSubTotalSinIva());
        orderEntity.setValorIva(orderDto.getValorIva());
        orderEntity.setTotalIncluyendoIva(orderDto.getTotalIncluyendoIva());
        orderEntity.setEntregado(orderDto.isEntregado());
        orderEntity.setFechaHoraEntrega(orderDto.getFechaHoraEntrega());

        return orderEntity;
    }

    public OrderDto toOrderDto(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setUuid(orderEntity.getUuid().toString());
        orderDto.setFechaHoraPedido(orderEntity.getFechaHoraPedido());
        orderDto.setCantidad(orderEntity.getCantidad());
        orderDto.setInformacionAdicional(orderEntity.getInformacionAdicional());
        orderDto.setSubTotalSinIva(orderEntity.getSubTotalSinIva());
        orderDto.setValorIva(orderEntity.getValorIva());
        orderDto.setTotalIncluyendoIva(orderEntity.getTotalIncluyendoIva());
        orderDto.setEntregado(orderEntity.isEntregado());
        orderDto.setFechaHoraEntrega(orderEntity.getFechaHoraEntrega());
        orderDto.setComboUuid(orderEntity.getCombo().getId().toString());
        orderDto.setDocument(orderEntity.getCliente().getDocument());

        return orderDto;
    }
}
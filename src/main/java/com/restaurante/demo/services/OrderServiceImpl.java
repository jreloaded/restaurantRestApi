package com.restaurante.demo.services;

import com.restaurante.demo.exceptions.clientException.CreateException;
import com.restaurante.demo.mapper.OrderMapper;
import com.restaurante.demo.modelo.dtos.OrderDto;
import com.restaurante.demo.modelo.dtos.OrderResponseDTO;
import com.restaurante.demo.repositories.ClientRepository;
import com.restaurante.demo.repositories.ComboRepository;
import com.restaurante.demo.repositories.OrderRepository;
import com.restaurante.demo.repositories.entity.ClientEntity;
import com.restaurante.demo.repositories.entity.CombosEntity;
import com.restaurante.demo.repositories.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDto createOrder(OrderResponseDTO orderResponseDTO) {

        Optional<ClientEntity> clientEntities = clientRepository.findByDocument(orderResponseDTO.getDocument());
        if (clientEntities.isEmpty()) {
            throw new CreateException(
                    "E-1004",
                    LocalDateTime.now(),
                    "RuntimeException",
                    HttpStatus.NOT_FOUND,
                    "Cliente no encontrado"
            );
        }
        ClientEntity clientEntity = (ClientEntity) clientEntities.get();

        Optional<CombosEntity> comboEntityOptional = comboRepository.findById(UUID.fromString(orderResponseDTO.getComboUuid()));
        if (!comboEntityOptional.isPresent()) {
            throw new CreateException(
                    "E-1001",
                    LocalDateTime.now(),
                    "Producto no encontrado",
                    HttpStatus.CONFLICT,
                    "Combo no encontrado");
        }

        OrderDto orderDto = orderMapper.toOrderDto(orderResponseDTO);
        OrderEntity orderEntity = orderMapper.toOrderEntity(orderDto, comboEntityOptional.get(), clientEntity);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        return orderMapper.toOrderDto(savedOrderEntity);
    }


    @Override
    @Transactional
    public OrderDto updateOrder(UUID uuid, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp, OrderResponseDTO orderResponseDTO) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(uuid);
        if (!orderEntityOptional.isPresent()) {
            throw new CreateException(
                    "E-1001",
                    LocalDateTime.now(),
                    "Orden no encontrada",
                    HttpStatus.CONFLICT,
                    "orden no encontrada");
        }

        OrderEntity orderEntity = orderEntityOptional.get();
        OrderDto orderDto = orderMapper.toOrderDto(orderResponseDTO);

        orderEntity.setFechaHoraPedido(orderDto.getFechaHoraPedido());
        orderEntity.setCantidad(orderDto.getCantidad());
        orderEntity.setInformacionAdicional(orderDto.getInformacionAdicional());
        orderEntity.setSubTotalSinIva(orderDto.getSubTotalSinIva());
        orderEntity.setValorIva(orderDto.getValorIva());
        orderEntity.setTotalIncluyendoIva(orderDto.getTotalIncluyendoIva());
        orderEntity.setEntregado(true);
        orderEntity.setFechaHoraEntrega(timestamp);


        if (!orderEntity.getCombo().getId().toString().equals(orderDto.getComboUuid())) {
            Optional<CombosEntity> comboEntityOptional = comboRepository.findById(UUID.fromString(orderDto.getComboUuid()));
            if (!comboEntityOptional.isPresent()) {
                throw new IllegalArgumentException("Combo no encontrado con UUID: " + orderDto.getComboUuid());
            }
            orderEntity.setCombo(comboEntityOptional.get());
        }

        if (!orderEntity.getCliente().getDocument().equals(orderDto.getDocument())) {
            Optional<ClientEntity> clientEntities = clientRepository.findByDocument(orderResponseDTO.getDocument());
            if (clientEntities.isEmpty()) {
                throw new IllegalArgumentException("Cliente no encontrado con documento: " + orderDto.getDocument());
            }
            ClientEntity clientEntity = (ClientEntity) clientEntities.get();
            orderEntity.setCliente(clientEntity);
        }

        OrderEntity updatedOrderEntity = orderRepository.save(orderEntity);

        return orderMapper.toOrderDto(updatedOrderEntity);
    }
}
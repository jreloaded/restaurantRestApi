package com.restaurante.demo.mapper;

import com.restaurante.demo.repositories.entity.ClientEntity;
import com.restaurante.demo.modelo.dtos.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper  {
    public ClientDto entityToDto(ClientEntity clientEntity) {
        return new ClientDto(
                clientEntity.getId_clientes(),
                clientEntity.getDocument(),
                clientEntity.getName(),
                clientEntity.getEmail(),
                clientEntity.getPhone(),
                clientEntity.getAddress()
        );
    }

    public ClientEntity dtoToEntity(ClientDto clientDto) {
        return new ClientEntity(
                clientDto.getIdClientes(),
                clientDto.getDocument(),
                clientDto.getName(),
                clientDto.getEmail(),
                clientDto.getPhone(),
                clientDto.getAddress()
        );
    }
}

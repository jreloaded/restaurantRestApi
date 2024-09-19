package com.restaurante.demo.services;

import com.restaurante.demo.modelo.dtos.ClientDto;
import com.restaurante.demo.repositories.entity.ClientEntity;

import java.util.List;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto);
    ClientDto getClientByDocumento(String documento);
    List<ClientDto> getAllClients();
    ClientDto updateClient(String  documento, ClientDto clientDto);
    void deleteClient(String  documento);
}

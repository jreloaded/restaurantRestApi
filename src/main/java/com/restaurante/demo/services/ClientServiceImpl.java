package com.restaurante.demo.services;

import com.restaurante.demo.exceptions.clientException.CreateException;
import com.restaurante.demo.mapper.ClientMapper;
import com.restaurante.demo.modelo.dtos.ClientDto;
import com.restaurante.demo.repositories.ClientRepository;
import com.restaurante.demo.repositories.entity.ClientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Optional<ClientEntity> existingClient = clientRepository.findByDocument(clientDto.getDocument());
        if (existingClient.isPresent()){
            throw new CreateException(
                    "E-1001",
                    LocalDateTime.now(),
                    "RuntimeException",
                    HttpStatus.CONFLICT,
                    "Cliente con documento: " + clientDto.getDocument() + "Ya existe");
        }
        ClientEntity clientEntity = clientMapper.dtoToEntity(clientDto);
        ClientEntity savedClient = clientRepository.save(clientEntity);
        return clientMapper.entityToDto(savedClient);
    }

    @Override
    public ClientDto getClientByDocumento(String document) {
        // Verificar el formato del documento
        if (!document.matches("^(CC|cc|Cc|cC|CE|ce|Ce|cE|P|p)-.*")) {
            throw new CreateException(
                    "E-1001",
                    LocalDateTime.now(),
                    "Formato de documento inválido",
                    HttpStatus.BAD_REQUEST,
                    "Se requiere que el documento comience con CC-, CE- o P-"
            );
        }


        Optional<ClientEntity> clientEntityOptional = clientRepository.findByDocument(document);
        if (clientEntityOptional.isPresent()) {
            return clientMapper.entityToDto(clientEntityOptional.get());
        }else{
            throw new CreateException(
                    "E-1004",
                    LocalDateTime.now(),
                    "RuntimeException",
                    HttpStatus.NOT_FOUND,
                    "Cliente no encontrado"
            );
        }
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto updateClient(String document, ClientDto clientDto) {
        Optional<ClientEntity> clientEntityOptional = clientRepository.findByDocument(document);
        if (clientEntityOptional.isPresent()) {
            ClientEntity clientEntity = clientEntityOptional.get();

            // Validar si hay campos diferentes en el cliente actualizado
            if (!clientDto.getName().equals(clientEntity.getName()) ||
                    !clientDto.getEmail().equals(clientEntity.getEmail()) ||
                    !clientDto.getPhone().equals(clientEntity.getPhone()) ||
                    !clientDto.getAddress().equals(clientEntity.getAddress())) {
                // Actualizar los campos del cliente
                clientEntity.setName(clientDto.getName());
                clientEntity.setEmail(clientDto.getEmail());
                clientEntity.setPhone(clientDto.getPhone());
                clientEntity.setAddress(clientDto.getAddress());
                ClientEntity updatedClient = clientRepository.save(clientEntity);
                return clientMapper.entityToDto(updatedClient);

            }else {
                throw new CreateException(
                        "E-1002",
                        LocalDateTime.now(),
                        "Campos iguales o desea cambiar el numero de documento recuerdas que este no se peude cambiar",
                        HttpStatus.CONFLICT,
                        "No hay cambios en los campos"
                );
            }

        } else {
            throw new CreateException(
                    "E-1004",
                    LocalDateTime.now(),
                    "RuntimeException",
                    HttpStatus.NOT_FOUND,
                    "Cliente no encontrado"
            );
        }

    }

    @Override
    public void deleteClient(String document) {
        // Verificar el formato del documento
        if (!document.matches("^(CC|cc|Cc|cC|CE|ce|Ce|cE|P|p)-.*")) {
            throw new CreateException(
                    "E-1001",
                    LocalDateTime.now(),
                    "Formato de documento inválido",
                    HttpStatus.BAD_REQUEST,
                    "Se requiere que el documento comience con CC-, CE- o P-"
            );
        }

        Optional<ClientEntity> clientEntityOptional = clientRepository.findByDocument(document);
        if (clientEntityOptional.isPresent()) {
            clientRepository.delete(clientEntityOptional.get());
        }else{
            throw new CreateException(
                    "E-1006",
                    LocalDateTime.now(),
                    "Cliente no existente",
                    HttpStatus.NOT_FOUND,
                    "No existe cliente con el número de documento suministrado"
            );
        }
    }
}

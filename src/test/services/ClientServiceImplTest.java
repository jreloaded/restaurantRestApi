package com.restaurante.demo.services;
import com.restaurante.demo.mapper.ClientMapper;
import com.restaurante.demo.modelo.dtos.ClientDto;
import com.restaurante.demo.repositories.ClientRepository;
import com.restaurante.demo.repositories.entity.ClientEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @InjectMocks
    private ClientServiceImpl clientServiceImpl;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createClient() {

        ClientDto clientDto = ClientDto.builder()
                .idClientes(1)
                .document("CC-10365438")
                .name("John")
                .email("Juan@globant.com")
                .phone("32032846343")
                .address("calle 61")
                .build();

        ClientEntity clientEntity = ClientEntity.builder()
                .id_clientes(1)
                .document("CC-10365438")
                .name("Juan")
                .email("Juan@globant.com")
                .phone("32032846343")
                .address("calle 61")
                .build();

        when(clientMapper.dtoToEntity(clientDto)).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        when(clientMapper.entityToDto(clientEntity)).thenReturn(clientDto);

        ClientDto result = clientServiceImpl.createClient(clientDto);

        assertNotNull(result);
        assertEquals(1, result.getIdClientes());
        assertEquals("CC-10365438", result.getDocument());
        assertEquals("John", result.getName());
        assertEquals("Juan@globant.com" ,result.getEmail());
        assertEquals("32032846343" ,result.getPhone());
        assertEquals("calle 61" ,result.getAddress());

    }




}
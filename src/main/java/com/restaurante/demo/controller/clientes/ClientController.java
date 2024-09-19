package com.restaurante.demo.controller.clientes;

import com.restaurante.demo.modelo.dtos.ClientDto;
import com.restaurante.demo.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<ClientDto> createClient(@RequestBody @Valid ClientDto clientDto) {
        ClientDto createdClient = clientService.createClient(clientDto);
        return ResponseEntity.ok(createdClient);
    }

    @GetMapping("/{documento}")
    public ResponseEntity<ClientDto> getClientByDocumento(@PathVariable String documento) {
        ClientDto clientDto = clientService.getClientByDocumento(documento);
        return clientDto != null ? ResponseEntity.ok(clientDto) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clientList = clientService.getAllClients();
        return ResponseEntity.ok(clientList);
    }

    @PutMapping("/{documento}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable String documento, @RequestBody @Valid ClientDto clientDto) {
        ClientDto updatedClient = clientService.updateClient(documento, clientDto);
        return updatedClient != null ? ResponseEntity.ok(updatedClient) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{documento}")
    public ResponseEntity<Void> deleteClient(@PathVariable String documento) {
        clientService.deleteClient(documento);
        return ResponseEntity.noContent().build();
    }
}
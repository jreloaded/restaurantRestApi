package com.restaurante.demo.repositories;

import com.restaurante.demo.repositories.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {

    Optional<ClientEntity> findByDocument(String document);
}

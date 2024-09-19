package com.restaurante.demo.repositories;

import com.restaurante.demo.repositories.entity.CombosEntity;
import com.restaurante.demo.repositories.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

}

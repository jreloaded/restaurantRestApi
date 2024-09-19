package com.restaurante.demo.repositories;

import com.restaurante.demo.repositories.entity.CombosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ComboRepository extends JpaRepository<CombosEntity, UUID> {
    Optional<CombosEntity> findByNombreCombo(String nombreCombo);
    @Query("SELECT c FROM CombosEntity c WHERE c.nombreCombo LIKE %:query% or c.categoria LIKE %:query%")
    List<CombosEntity> findByQuery(String query);
}

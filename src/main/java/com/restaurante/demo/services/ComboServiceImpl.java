package com.restaurante.demo.services;


import com.restaurante.demo.exceptions.clientException.CreateException;
import com.restaurante.demo.modelo.dtos.CombosDto;
import com.restaurante.demo.repositories.ComboRepository;
import com.restaurante.demo.repositories.entity.CombosEntity;
import com.restaurante.demo.mapper.CombosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ComboServiceImpl implements ComboService {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private CombosMapper combosMapper;

    @Override
    public List<CombosDto> getAllCombos() {
        List<CombosEntity> combos = comboRepository.findAll();
        return combos.stream()
                .map(combosMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CombosDto createCombo(CombosDto combosDto) {

        String nombreCombo = combosDto.getNombreCombo();
        Optional<CombosEntity> existingCombos = comboRepository.findByNombreCombo(combosDto.getNombreCombo());
        if (existingCombos.isPresent()){
            throw new CreateException(
                    "E-1001",
                    LocalDateTime.now(),
                    "RuntimeException",
                    HttpStatus.CONFLICT,
                    "El combo con nombre: " + combosDto.getNombreCombo()+ "Ya existe");
        }


        CombosEntity comboEntity = combosMapper.toEntity(combosDto);
        CombosEntity savedCombo = comboRepository.save(comboEntity);
        return combosMapper.toDTO(savedCombo);
    }

    @Override
    public CombosDto getComboByUuid(UUID uuid) {
        Optional<CombosEntity> combo = comboRepository.findById(uuid);
        if (combo.isPresent()){
            return combo.map(combosMapper::toDTO).orElse(null);
        }
            throw new CreateException(
                    "E-1004",
                    LocalDateTime.now(),
                    "Combo no encontrado",
                    HttpStatus.NOT_FOUND,
                    "Combo no encontrado"
            );

    }




    @Override
    public void deleteCombo(UUID uuid) {

        Optional<CombosEntity> combosEntityOptional = comboRepository.findById(uuid);

        if (!combosEntityOptional.isPresent()){
            throw new CreateException(
                    "E-1001",
                    LocalDateTime.now(),
                    "Producto no encontrado",
                    HttpStatus.CONFLICT,
                    "Combo no encontrado");
        }



        comboRepository.deleteById(uuid);
    }

    @Override
    public CombosDto updateCombo(UUID uuid, CombosDto combosDto) {
        Optional<CombosEntity> combosEntityOptional = comboRepository.findById(uuid);
        Optional<CombosEntity> existingCombos = comboRepository.findByNombreCombo(combosDto.getNombreCombo());

        if (existingCombos.isPresent()){
            throw new CreateException(
                    "E-1001",
                    LocalDateTime.now(),
                    "Como ya existente",
                    HttpStatus.CONFLICT,
                    "El combo con nombre: " + combosDto.getNombreCombo()+ "Ya existe");
        }


        CombosEntity combosEntity = combosEntityOptional.get();



        if (combosEntityOptional.isPresent()) {
            CombosEntity comboEntity = combosEntityOptional.get();

            // Validar si hay campos diferentes en el cliente actualizado
            if (!combosDto.getNombreCombo().equals(comboEntity.getNombreCombo()) ||
                    !combosDto.getCategoria().equals(comboEntity.getCategoria()) ||
                    !combosDto.getDescripcion().equals(comboEntity.getDescripcion()) ||
                    combosDto.getPrecioAntesIva() != comboEntity.getPrecioAntesIva() ||
                    combosDto.isDisponible() != comboEntity.isDisponible()
            ) {
                // Actualizar los campos del combo
                comboEntity.setNombreCombo(combosDto.getNombreCombo());
                comboEntity.setCategoria(combosDto.getCategoria());
                comboEntity.setDescripcion(combosDto.getDescripcion());
                comboEntity.setPrecioAntesIva(combosDto.getPrecioAntesIva());
                comboEntity.setDisponible(combosDto.isDisponible());
                CombosEntity updatedCombo = comboRepository.save(comboEntity);

                return combosMapper.toDTO(updatedCombo);

            } else {
                throw new CreateException(
                        "E-1002",
                        LocalDateTime.now(),
                        "Campos iguales",
                        HttpStatus.CONFLICT,
                        "No hay cambios en los campos"
                );
            }


        }
            throw new CreateException(
                    "E-1004",
                    LocalDateTime.now(),
                    "Combo no encontrado",
                    HttpStatus.NOT_FOUND,
                    "Combo no encontrado"
            );


    }

    // bonus
    @Override
    public List<CombosDto> getSearchcombos(String query) {
        List<CombosEntity> combos = comboRepository.findByQuery(query);
        return combos.stream()
                .map(combosMapper::toDTO)
                .collect(Collectors.toList());
    }



}
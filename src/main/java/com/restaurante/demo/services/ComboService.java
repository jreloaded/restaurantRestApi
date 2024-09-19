package com.restaurante.demo.services;

import com.restaurante.demo.modelo.dtos.CombosDto;
import java.util.List;
import java.util.UUID;

public interface ComboService {
    CombosDto createCombo(CombosDto comboDto);
    CombosDto getComboByUuid(UUID uuid);
    List<CombosDto> getAllCombos();
    CombosDto updateCombo(UUID uuid, CombosDto comboDto);
    void deleteCombo(UUID uuid);
    List<CombosDto> getSearchcombos(String query);
}

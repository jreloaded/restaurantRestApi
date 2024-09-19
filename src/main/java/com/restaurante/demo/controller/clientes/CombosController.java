package com.restaurante.demo.controller.clientes;


import com.restaurante.demo.exceptions.clientException.CreateException;
import com.restaurante.demo.modelo.dtos.CombosDto;
import com.restaurante.demo.services.ComboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/combos")
public class CombosController {

    @Autowired
    private ComboService comboService;

    @GetMapping("/all")
    public ResponseEntity<List<CombosDto>> getAllCombos() {
        List<CombosDto> combosList = comboService.getAllCombos();
        return ResponseEntity.ok(combosList);
    }

    @PostMapping("/create")
    public ResponseEntity<CombosDto> createCombo(@RequestBody @Valid CombosDto combosDto) {
        CombosDto newComboDto = comboService.createCombo(combosDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newComboDto);
    }

    @GetMapping("/by-uuid/{uuid}")
    public ResponseEntity<CombosDto> getComboByUuid(@PathVariable String uuid) {
            if (isValidUuid(uuid)){
                CombosDto comboDto = comboService.getComboByUuid(UUID.fromString(uuid));
                if (comboDto != null) {
                    return ResponseEntity.ok(comboDto);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            }
                 throw new CreateException(
                        "E-1001",
                        LocalDateTime.now(),
                        "Uuid invalido",
                        HttpStatus.CONFLICT,
                        "Uuid invalido ");

    }

    private boolean isValidUuid (String uuid){
        try{
            UUID comboUuid = UUID.fromString(uuid);
            return true;
        }catch (IllegalArgumentException e){
            return false;
        }
    }


    @DeleteMapping("/by-uuid/{uuid}")
    public ResponseEntity<Void> deleteCombo(@PathVariable String uuid) {
        if (isValidUuid(uuid)){
            UUID comboUuid = UUID.fromString(uuid);
            comboService.deleteCombo(comboUuid);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }  throw new CreateException(
                "E-1001",
                LocalDateTime.now(),
                "Uuid invalido",
                HttpStatus.CONFLICT,
                "Uuid invalido ");
    }

    @PutMapping("/by-uuid/{uuid}")
    public ResponseEntity<CombosDto> updateCombo(@PathVariable String uuid, @RequestBody @Valid CombosDto combosDto) {
        try {
            UUID comboUuid = UUID.fromString(uuid);
            CombosDto updatedCombo = comboService.updateCombo(comboUuid, combosDto);
            if (updatedCombo != null) {
                return ResponseEntity.ok(updatedCombo);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



    //Bonus

    @GetMapping("/search")
    public  ResponseEntity<List<CombosDto>> getSearchcombos(@RequestParam(name = "q") String query) {
        if(query.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<CombosDto> combosList = comboService.getSearchcombos(query);

        if(combosList.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(combosList);
    }


}
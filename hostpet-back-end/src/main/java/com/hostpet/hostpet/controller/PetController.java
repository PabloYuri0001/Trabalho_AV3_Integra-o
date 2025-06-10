package com.hostpet.hostpet.controller;

import com.hostpet.hostpet.dtos.PetsListDTO;
import com.hostpet.hostpet.entity.Pet;
import com.hostpet.hostpet.enums.Sexo;
import com.hostpet.hostpet.forms.PetForm;
import com.hostpet.hostpet.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/pets")
@Validated
public class PetController {

    @Autowired
    private PetService petService;

    // Endpoint para cadastrar um novo Pet
    @PostMapping
    public ResponseEntity<Pet> cadastrarPet(@RequestBody @Valid PetForm form) {
        return ResponseEntity.ok(petService.savePet(form));
    }

    // Endpoint para listar todos os Pets
//    @GetMapping
//    public ResponseEntity<List<Pet>> listarPets() {
//        return ResponseEntity.ok(petService.getAllPets());
//    }

    @GetMapping
    public ResponseEntity<List<PetsListDTO>> listarPetsByUser(@RequestParam(required = true) Long userId,
                                                              @RequestParam(required = false) String nome,
                                                              @RequestParam(required = false) Sexo sexo) {

        return ResponseEntity.ok(petService.getPetsByUser(userId,nome,sexo));
    }

    //Endpoint para Editar um pet
    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizarPet(@PathVariable Integer id, @RequestBody Pet petAtualizado) {
        return ResponseEntity.ok(petService.updatePet(id, petAtualizado));
    }

    // Endpoint para excluir um Pet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPet(@PathVariable Integer id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }
}

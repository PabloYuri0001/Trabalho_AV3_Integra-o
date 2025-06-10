package com.hostpet.hostpet.controller;

import com.hostpet.hostpet.dtos.BaiaStatusDTO;
import com.hostpet.hostpet.entity.Baia;
import com.hostpet.hostpet.forms.BaiaForm;
import com.hostpet.hostpet.services.BaiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/baias")
public class BaiaController {

    @Autowired
    private BaiaService baiaService;

    // Endpoint para cadastrar uma nova baia
    @PostMapping
    public ResponseEntity<Baia> cadastrarBaia(@RequestBody @Valid BaiaForm baiaForm) {
        Baia novaBaia = baiaService.saveBaia(baiaForm);
        return ResponseEntity.ok(novaBaia);
    }

    // Endpoint para listar todas as baias de um user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Baia>> listarBaias(@PathVariable Long userId) {
        List<Baia> baias = baiaService.getAllBaiasByUser(userId);
        return ResponseEntity.ok(baias);
    }

    // Endpoint para excluir uma baia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirBaia(@PathVariable Integer id) {
        baiaService.deleteBaia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<BaiaStatusDTO> getTotaisPorStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(baiaService.calcularTotaisBaia(userId));
    }
}

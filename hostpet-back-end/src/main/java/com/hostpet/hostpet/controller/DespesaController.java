package com.hostpet.hostpet.controller;

import com.hostpet.hostpet.dtos.FinanceiroDespesaDTO;
import com.hostpet.hostpet.entity.Despesa;
import com.hostpet.hostpet.forms.DespesaFomr;
import com.hostpet.hostpet.services.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Despesa>> listarDespesasPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(despesaService.listarDespesasPorUsuario(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> listDespesasById(@PathVariable Long id) {
        Optional<Despesa> despesa = despesaService.listDespesaById(id);
        return despesa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Despesa> criarDespesa(@Valid @RequestBody DespesaFomr form) {
        Despesa novaDespesa = despesaService.cadastrarDespesa(form);
        return ResponseEntity.ok(novaDespesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDespesa(@PathVariable Long id) {
        despesaService.excluirDespesa(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/totais/{userId}")
    public FinanceiroDespesaDTO getTotaisDespesas(@PathVariable Long userId) {
        return despesaService.getTotaisDespesas(userId);
    }
}

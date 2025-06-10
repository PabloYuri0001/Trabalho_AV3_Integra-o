package com.hostpet.hostpet.controller;

import com.hostpet.hostpet.entity.Cliente;
import com.hostpet.hostpet.forms.ClienteForm;
import com.hostpet.hostpet.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Validated
public class ClienteController {
    @Autowired
    private ClienteService clienteService;


    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid ClienteForm clienteForm) {
        return ResponseEntity.ok(clienteService.cadastrarCliente(clienteForm));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Cliente>> listClientesByUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(clienteService.listClientesByUser(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        clienteService.excluirCliente(id);
        return ResponseEntity.noContent().build();
    }
}
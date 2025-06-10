package com.hostpet.hostpet.controller;

import com.hostpet.hostpet.dtos.FinanceiroEntradaDTO;
import com.hostpet.hostpet.entity.Agendamento;
import com.hostpet.hostpet.forms.AgendamentoForm;
import com.hostpet.hostpet.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    // Rota para cadastrar um novo agendamento usando o formulário
    @PostMapping
    public ResponseEntity<Agendamento> cadastrarAgendamento(@Valid @RequestBody AgendamentoForm form) {
        Agendamento agendamentoSalvo = agendamentoService.saveAgendamento(form);
        return ResponseEntity.ok(agendamentoSalvo);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Agendamento>> listAgendamentosByUser(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(agendamentoService.listAgendamentosByUser(userId));
    }

    @GetMapping("/totais/pagos")
    public ResponseEntity<FinanceiroEntradaDTO> getTotaisPagos() {
        return ResponseEntity.ok(agendamentoService.getTotaisPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarAgendamentoPorId(@PathVariable Integer id) {
        Agendamento agendamento = agendamentoService.buscarAgendamentoPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable Integer id) {
        agendamentoService.excluirAgendamento(id);
        return ResponseEntity.noContent().build();
    }
}

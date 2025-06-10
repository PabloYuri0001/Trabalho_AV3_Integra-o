package com.hostpet.hostpet.services;

import com.hostpet.hostpet.dtos.FinanceiroDespesaDTO;
import com.hostpet.hostpet.entity.Despesa;
import com.hostpet.hostpet.entity.User;
import com.hostpet.hostpet.forms.DespesaFomr;
import com.hostpet.hostpet.repository.DespesaRepository;
import com.hostpet.hostpet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private UserRepository userRepository;

    public Despesa cadastrarDespesa(DespesaFomr fomr){
        Optional<User> userOpt = userRepository.findById(fomr.getUserId());
        if (userOpt.isEmpty()){
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        Despesa despesa = new Despesa();
        despesa.setDescricao(fomr.getDescricao());
        despesa.setCategoria(fomr.getCategoria());
        despesa.setValor(fomr.getValor());
        despesa.setDataDespesa(LocalDateTime.now());
        despesa.setUser(userOpt.get());

        return despesaRepository.save(despesa);
    }



    public FinanceiroDespesaDTO getTotaisDespesas(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        LocalDateTime hojeInicio = LocalDate.now().atStartOfDay();
        LocalDateTime hojeFim = hojeInicio.plusDays(1).minusSeconds(1);

        LocalDateTime inicioMes = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime fimMes = inicioMes.plusMonths(1).minusSeconds(1);

        LocalDate hoje = LocalDate.now();
        DayOfWeek primeiroDiaSemana = DayOfWeek.SUNDAY;
        LocalDate inicioSemana = hoje.with(TemporalAdjusters.previousOrSame(primeiroDiaSemana));
        LocalDate fimSemana = inicioSemana.plusDays(6);

        BigDecimal totalGeral = despesaRepository.getTotalGeral(userId);
        BigDecimal totalMensal = despesaRepository.getTotalEntreDatas(userId, inicioMes, fimMes);
        BigDecimal totalSemanal = despesaRepository.getTotalEntreDatas(userId, inicioSemana.atStartOfDay(), fimSemana.atTime(23, 59, 59));
        BigDecimal totalDiario = despesaRepository.getTotalEntreDatas(userId, hojeInicio, hojeFim);

        return new FinanceiroDespesaDTO(totalGeral, totalMensal, totalSemanal, totalDiario);
    }


    public List<Despesa> listarDespesasPorUsuario(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return despesaRepository.findByUserId(userId);
    }

    public Optional<Despesa> listDespesaById(Long id) {
        return despesaRepository.findById(id);
    }

    public void excluirDespesa(Long id) {
        despesaRepository.deleteById(id);
    }
}
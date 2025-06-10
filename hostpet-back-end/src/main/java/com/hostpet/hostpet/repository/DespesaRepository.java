package com.hostpet.hostpet.repository;

import com.hostpet.hostpet.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM Despesa d WHERE d.user.id = :userId")
    BigDecimal getTotalGeral(@Param("userId") Long userId);

    @Query("SELECT COALESCE(SUM(d.valor), 0) FROM Despesa d WHERE d.user.id = :userId AND d.dataDespesa BETWEEN :inicio AND :fim")
    BigDecimal getTotalEntreDatas(@Param("userId") Long userId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}

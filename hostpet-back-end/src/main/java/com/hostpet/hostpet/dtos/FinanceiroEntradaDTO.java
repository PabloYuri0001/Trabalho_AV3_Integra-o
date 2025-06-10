package com.hostpet.hostpet.dtos;

import java.math.BigDecimal;

public class FinanceiroEntradaDTO {

    private BigDecimal totalGeral;
    private BigDecimal totalMensal;
    private BigDecimal totalSemanal;
    private BigDecimal totalDiario;

    public FinanceiroEntradaDTO(BigDecimal totalGeral, BigDecimal totalMensal, BigDecimal totalSemanal, BigDecimal totalDiario) {
        this.totalGeral = totalGeral;
        this.totalMensal = totalMensal;
        this.totalSemanal = totalSemanal;
        this.totalDiario = totalDiario;
    }

    public BigDecimal getTotalGeral() { return totalGeral; }
    public BigDecimal getTotalMensal() { return totalMensal; }
    public BigDecimal getTotalSemanal() { return totalSemanal; }
    public BigDecimal getTotalDiario() { return totalDiario; }

    public void setTotalGeral(BigDecimal totalGeral) { this.totalGeral = totalGeral; }
    public void setTotalMensal(BigDecimal totalMensal) { this.totalMensal = totalMensal; }
    public void setTotalSemanal(BigDecimal totalSemanal) { this.totalSemanal = totalSemanal; }
    public void setTotalDiario(BigDecimal totalDiario) { this.totalDiario = totalDiario; }
}

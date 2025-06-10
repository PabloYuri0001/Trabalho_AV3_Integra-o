package com.hostpet.hostpet.forms;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AgendamentoForm {

    @NotNull(message = "A data de início do agendamento é obrigatória    ")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHoraInicio;

    @NotNull(message = "A data de fim do agendamento é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHoraFim;


    @NotNull(message = "O valor do agendamento é obrigatório")
    private BigDecimal valor;

    @NotBlank(message = "A forma de pagamento é obrigatória")
    @Size(max = 50, message = "A forma de pagamento deve ter no máximo 50 caracteres")
    private String formaPagamento;

    @NotBlank(message = "O status de pagamento é obrigatório")
    @Size(max = 50, message = "O status de pagamento deve ter no máximo 50 caracteres")
    private String statusPagamento;



    @NotNull(message = "O id do pet é obrigatório")
    private Integer idPet;

    @NotNull(message = "O id da baia é obrigatório")
    private Integer idBaia;

    @NotNull(message = "O id do usuário é obrigatório")
    private Long userId;

    // Getters and Setters


    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }


    public Integer getIdPet() {
        return idPet;
    }

    public void setIdPet(Integer idPet) {
        this.idPet = idPet;
    }

    public Integer getIdBaia() {
        return idBaia;
    }

    public void setIdBaia(Integer idBaia) {
        this.idBaia = idBaia;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


}

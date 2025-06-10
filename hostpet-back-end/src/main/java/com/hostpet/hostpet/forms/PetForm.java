package com.hostpet.hostpet.forms;

import com.hostpet.hostpet.enums.Sexo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public class PetForm {
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    public String nome;

    @NotNull(message = "O sexo é obrigatório")
    public Sexo sexo;

    @NotBlank(message = "A raça do pet é obrigatório")
    public String racaPet;

    @Size(max = 255, message = "As observações devem ter no máximo 255 caracteres")
    public String observacoes;

    @NotNull(message = "A data de nascimento é obrigatória")
    public LocalDate dtNascimento;

    @NotNull(message = "O id do cliente é obrigatório")
    public Long clienteId;
}
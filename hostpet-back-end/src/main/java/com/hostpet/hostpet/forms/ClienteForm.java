package com.hostpet.hostpet.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ClienteForm {
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    public String nome;

    @NotBlank(message = "O telefone é obrigatório")
    public String telefone;

    @NotNull(message = "O usuário é obrigatório")
    public Long userId;
}
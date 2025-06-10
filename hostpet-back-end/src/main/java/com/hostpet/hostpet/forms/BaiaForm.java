package com.hostpet.hostpet.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BaiaForm {

    @NotBlank(message = "o nome é obrigatório")
    public String descricao;
    @NotNull(message = "O usuário é obrigatório")
    public Long userId;
}

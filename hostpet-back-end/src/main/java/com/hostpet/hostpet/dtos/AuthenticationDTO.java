package com.hostpet.hostpet.dtos;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(@NotNull(message = "Email nao pode ser nulo") String email , String password) {


}

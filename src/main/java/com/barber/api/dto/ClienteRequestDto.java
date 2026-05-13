package com.barber.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequestDto {
    @NotBlank(message = "É obrigatório")
    @Size(min = 3, max = 120, message = "De 3 a 120 caracteres")
    private String nome;

    @NotBlank(message = "É obrigatório")
    @Size(min = 9, max = 11, message = "De 9 a 11 caracteres")
    private String telefone;
}

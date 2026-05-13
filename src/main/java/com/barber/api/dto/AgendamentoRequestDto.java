package com.barber.api.dto;

import com.barber.api.entity.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoRequestDto {

    private Long clienteId;

    private LocalDateTime dataHora;

}

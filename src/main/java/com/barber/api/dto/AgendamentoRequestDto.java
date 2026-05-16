package com.barber.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AgendamentoRequestDto {

    private Long clienteId;

    private LocalDateTime dataHora;

}

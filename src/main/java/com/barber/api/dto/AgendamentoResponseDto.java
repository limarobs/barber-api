package com.barber.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoResponseDto {
    private Long id;
    private LocalDateTime dataHora;
    private String clienteNome;
    private String clienteTelefone;


}

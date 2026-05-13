package com.barber.api.repository;

import com.barber.api.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoRepository
        extends JpaRepository<Agendamento, Long> {

    boolean existsByDataHora(LocalDateTime dataHora);

}

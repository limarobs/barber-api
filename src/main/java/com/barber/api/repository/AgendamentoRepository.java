package com.barber.api.repository;

import com.barber.api.dto.AgendamentoResponseDto;
import com.barber.api.entity.Agendamento;
import com.barber.api.enums.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository
        extends JpaRepository<Agendamento, Long> {

    boolean existsByDataHora(LocalDateTime dataHora);
    
    List<Agendamento> findByStatus(StatusAgendamento status);

    List<Agendamento> findByDataHoraBetweenAndStatus(LocalDateTime inicio, LocalDateTime fim, StatusAgendamento status);



}

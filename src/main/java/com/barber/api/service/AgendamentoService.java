package com.barber.api.service;

import com.barber.api.dto.AgendamentoRequestDto;
import com.barber.api.dto.AgendamentoResponseDto;
import com.barber.api.entity.Agendamento;
import com.barber.api.entity.Cliente;
import com.barber.api.enums.StatusAgendamento;
import com.barber.api.exception.OperacaoInvalidaException;
import com.barber.api.exception.DataInvalidaException;
import com.barber.api.exception.HorarioIndisponivelException;
import com.barber.api.repository.AgendamentoRepository;
import com.barber.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AgendamentoService {
    private final AgendamentoRepository repository;
    private final ClienteRepository crepository;
    private final ClienteService service;


    private AgendamentoResponseDto toResponseDto(Agendamento agendamento){
        return new AgendamentoResponseDto(
                agendamento.getId(),
                agendamento.getDataHora(),
                agendamento.getCliente().getTelefone(),
                agendamento.getCliente().getNome()
        );
    }

    private void validarCancelamento(Agendamento agendamento){
        if(agendamento.getStatus() == StatusAgendamento.CANCELADO)
            throw new OperacaoInvalidaException("Esse agendamento já está cancelado");

        if(agendamento.getStatus() == StatusAgendamento.FINALIZADO)
            throw new OperacaoInvalidaException("Esse agendamento está finalizado");
    }

    private void validarFinalizacao (Agendamento agendamento){
        if(agendamento.getStatus() == StatusAgendamento.CANCELADO)
            throw new OperacaoInvalidaException("Esse agendamento está cancelado");

        if(agendamento.getStatus() == StatusAgendamento.FINALIZADO)
            throw new OperacaoInvalidaException("Esse agendamento já está finalizado");
    }

    // --

    public List<AgendamentoResponseDto> listaAgendamentos(){
        List <Agendamento> agendamentos = repository.findAll();
        List <AgendamentoResponseDto> response = new ArrayList<>();

        for(Agendamento agendamento : agendamentos){
            AgendamentoResponseDto dto = toResponseDto(agendamento);
            response.add(dto);
        }

        return response;

    }

    public AgendamentoResponseDto listaAgendamentoId(Long id){
        Optional<Agendamento> agendamentoOptional = repository.findById(id);
        Agendamento agendamento = agendamentoOptional.get();
        return toResponseDto(agendamento);
    }

    public List<AgendamentoResponseDto> listaAgendamentosAtivos(){
        List <Agendamento> agendamentos = repository.findByStatus(StatusAgendamento.AGENDADO);
        List <AgendamentoResponseDto> response = new ArrayList<>();

        for(Agendamento agendamento : agendamentos){
            AgendamentoResponseDto dto = toResponseDto(agendamento);
            response.add(dto);
        }
        return response;
    }


        public AgendamentoResponseDto atualizaAgendamento(Long id, AgendamentoRequestDto request){
        Optional<Agendamento> agendamentoOptional = repository.findById(id);
        Agendamento agendamento = agendamentoOptional.get();

        if(repository.existsByDataHora(request.getDataHora())){
            throw new HorarioIndisponivelException("Horário indisponível");
        }

        Optional <Cliente> clienteOptional = crepository.findById(request.getClienteId());

        Cliente cliente = clienteOptional.get();

        agendamento.setDataHora(request.getDataHora());
        agendamento.setCliente(cliente);

        Agendamento salvo =  repository.save(agendamento);

        return toResponseDto(salvo);
    }

    public AgendamentoResponseDto cancelaAgendamento(Long id){
        Optional<Agendamento> agendamentoOptional = repository.findById(id);
        Agendamento agendamento = agendamentoOptional.get();

        validarCancelamento(agendamento);

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        Agendamento salvo = repository.save(agendamento);

        return toResponseDto(salvo);
    }

    public AgendamentoResponseDto finalizaAgendamento(Long id){
        Optional<Agendamento> agendamentoOptional = repository.findById(id);
        Agendamento agendamento = agendamentoOptional.get();

        validarFinalizacao(agendamento);

        agendamento.setStatus(StatusAgendamento.FINALIZADO);
        Agendamento salvo = repository.save(agendamento);

        return toResponseDto(salvo);

    }

    public AgendamentoResponseDto criaAgendamento(AgendamentoRequestDto request){
        LocalTime horario = request.getDataHora().toLocalTime();

        LocalTime abertura = LocalTime.of(9, 0);

        LocalTime fechamento = LocalTime.of(19, 0);

        if(repository.existsByDataHora(request.getDataHora())){
            throw new HorarioIndisponivelException("Horário indisponível");
        }

        if(request.getDataHora().isBefore(LocalDateTime.now())){
            throw new DataInvalidaException("Não é possível agendar no passado");
        }

        if(horario.isBefore(abertura) || horario.isAfter(fechamento)){
            throw new HorarioIndisponivelException("Não é possível agendar no passado");
        }

        Agendamento agendamento = new Agendamento();

        Cliente cliente = service.listaClienteId(request.getClienteId());

        agendamento.setCliente(cliente);
        agendamento.setDataHora(request.getDataHora());

        agendamento.setStatus(StatusAgendamento.AGENDADO);
        Agendamento salvo = repository.save(agendamento);

        return toResponseDto(salvo);
    }





}

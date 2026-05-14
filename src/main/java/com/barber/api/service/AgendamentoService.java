package com.barber.api.service;

import com.barber.api.dto.AgendamentoRequestDto;
import com.barber.api.dto.AgendamentoResponseDto;
import com.barber.api.entity.Agendamento;
import com.barber.api.entity.Cliente;
import com.barber.api.exception.HorarioIndisponivelException;
import com.barber.api.repository.AgendamentoRepository;
import com.barber.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void deletaAgendamento(Long id){
        repository.deleteById(id);
    }

    public AgendamentoResponseDto criaAgendamento(AgendamentoRequestDto request){
        if(repository.existsByDataHora(request.getDataHora())){
            throw new HorarioIndisponivelException("Horário indisponível");
        }

        Agendamento agendamento = new Agendamento();

        Cliente cliente = service.listaClienteId(request.getClienteId());

        agendamento.setCliente(cliente);
        agendamento.setDataHora(request.getDataHora());

        Agendamento salvo = repository.save(agendamento);

        return toResponseDto(salvo);
    }





}

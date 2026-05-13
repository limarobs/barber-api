package com.barber.api.service;

import com.barber.api.dto.AgendamentoRequestDto;
import com.barber.api.entity.Agendamento;
import com.barber.api.entity.Cliente;
import com.barber.api.exception.HorarioIndisponivelException;
import com.barber.api.repository.AgendamentoRepository;
import com.barber.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AgendamentoService {
    private final AgendamentoRepository repository;
    private final ClienteRepository crepository;
    private final ClienteService service;

    public List<Agendamento> listaAgendamentos(){
        return repository.findAll();
    }

    public Agendamento listaAgendamentoId(Long id){
        Optional<Agendamento> agendamento = repository.findById(id);
        return agendamento.get();
    }

    public Agendamento atualizaAgendamento(Long id, AgendamentoRequestDto request){
        Optional<Agendamento> agendamentoOptional = repository.findById(id);
        Agendamento agendamento = agendamentoOptional.get();

        if(repository.existsByDataHora(request.getDataHora())){
            throw new HorarioIndisponivelException("Horário indisponível");
        }

        Optional <Cliente> clienteOptional = crepository.findById(request.getClienteId());

        Cliente cliente = clienteOptional.get();

        agendamento.setDataHora(request.getDataHora());
        agendamento.setCliente(cliente);

        return repository.save(agendamento);

    }


    public void deletaAgendamento(Long id){
        repository.deleteById(id);
    }

    public Agendamento criaAgendamento(AgendamentoRequestDto request){

        if(repository.existsByDataHora(request.getDataHora())){
            throw new HorarioIndisponivelException("Horário indisponível");
        }

        Agendamento agendamento = new Agendamento();
        Cliente cliente = service.listaClienteId(request.getClienteId());
        agendamento.setCliente(cliente);
        agendamento.setDataHora(request.getDataHora());

        return repository.save(agendamento);

    }



}

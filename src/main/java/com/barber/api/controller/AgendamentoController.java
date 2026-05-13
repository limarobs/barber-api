package com.barber.api.controller;


import com.barber.api.dto.AgendamentoRequestDto;
import com.barber.api.entity.Agendamento;
import com.barber.api.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @GetMapping
    public List<Agendamento> listarAgendamentos(){
        return service.listaAgendamentos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> listarAgendamentoId(@PathVariable Long id){
        Agendamento agendamento = service.listaAgendamentoId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity <Agendamento> atualizarAgendamento(@PathVariable Long id, @RequestBody AgendamentoRequestDto request){
        Agendamento atualizado = service.atualizaAgendamento(id, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id){
        service.deletaAgendamento(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody AgendamentoRequestDto request){
        Agendamento criado = service.criaAgendamento(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }
}

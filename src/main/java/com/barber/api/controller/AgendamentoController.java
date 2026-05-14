package com.barber.api.controller;


import com.barber.api.dto.AgendamentoRequestDto;
import com.barber.api.dto.AgendamentoResponseDto;
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

    //GET
    @GetMapping
    public List<AgendamentoResponseDto> listarAgendamentos(){
        return service.listaAgendamentos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> listarAgendamentoId(@PathVariable Long id){
        AgendamentoResponseDto agendamento = service.listaAgendamentoId(id);
        return ResponseEntity.ok(agendamento);
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity <AgendamentoResponseDto> atualizarAgendamento(@PathVariable Long id, @RequestBody AgendamentoRequestDto request){
        AgendamentoResponseDto atualizado = service.atualizaAgendamento(id, request);
        return ResponseEntity.ok(atualizado);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable Long id){
        service.deletaAgendamento(id);

        return ResponseEntity.noContent().build();
    }

    //POST
    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> criarAgendamento(@RequestBody AgendamentoRequestDto request){
        AgendamentoResponseDto criado = service.criaAgendamento(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }
}

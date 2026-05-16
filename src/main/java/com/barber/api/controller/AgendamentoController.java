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

    @GetMapping("/ativos")
    public List<AgendamentoResponseDto> listarAgendamentosAtivos(){
        return service.listaAgendamentosAtivos();
    }

    @GetMapping("/hoje")
    public List<AgendamentoResponseDto> listarAgendamentosHoje(){
        return service.listaAgendamentosHoje();
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity <AgendamentoResponseDto> atualizarAgendamento(@PathVariable Long id, @RequestBody AgendamentoRequestDto request){
        AgendamentoResponseDto atualizado = service.atualizaAgendamento(id, request);
        return ResponseEntity.ok(atualizado);
    }

    //PATCH
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoResponseDto> cancelarAgendamento(@PathVariable Long id){
        AgendamentoResponseDto cancelado = service.cancelaAgendamento(id);

        return ResponseEntity.ok(cancelado);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<AgendamentoResponseDto> finalizarAgendamento(@PathVariable Long id){
        AgendamentoResponseDto finalizado = service.finalizaAgendamento(id);

        return ResponseEntity.ok(finalizado);
    }

    //POST
    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> criarAgendamento(@RequestBody AgendamentoRequestDto request){
        AgendamentoResponseDto criado = service.criaAgendamento(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }
}

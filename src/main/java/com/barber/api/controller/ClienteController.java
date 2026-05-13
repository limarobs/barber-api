package com.barber.api.controller;

import com.barber.api.dto.ClienteRequestDto;
import com.barber.api.entity.Cliente;
import com.barber.api.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public List<Cliente> listarClientes(){
        return service.listaClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listarClienteId(@PathVariable Long id){
        Cliente cliente = service.listaClienteId(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> attCliente(@PathVariable Long id, @RequestBody @Valid ClienteRequestDto request){
        Cliente atualizado = service.atualizaCliente(id, request);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id){
        service.deletaCliente(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> addCliente(@RequestBody @Valid ClienteRequestDto request){
        Cliente criado = service.adicionaCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }



}

package com.barber.api.service;

import com.barber.api.dto.ClienteRequestDto;
import com.barber.api.entity.Cliente;
import com.barber.api.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClienteService {
    private final ClienteRepository repository;

    public List<Cliente> listaClientes(){
        return repository.findAll();
    }

    public Cliente listaClienteId(Long id){
        Optional<Cliente> cliente = repository.findById(id);
        return cliente.get();
    }

    public void deletaCliente(Long id){
        repository.deleteById(id);
    }

    public Cliente adicionaCliente(ClienteRequestDto request){
        Cliente cliente = new Cliente();

        cliente.setNome(request.getNome());
        cliente.setTelefone(request.getTelefone());

        return repository.save(cliente);
    }

    public Cliente atualizaCliente(Long id, ClienteRequestDto request){
        Optional<Cliente> clienteOptional = repository.findById(id);

        Cliente cliente = clienteOptional.get();

        cliente.setNome(request.getNome());
        cliente.setTelefone(request.getTelefone());

        return repository.save(cliente);
    }


}

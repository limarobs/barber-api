package com.barber.api.controller;

import com.barber.api.dto.LoginRequestDto;
import com.barber.api.dto.LoginResponseDto;
import com.barber.api.entity.Usuario;
import com.barber.api.repository.UsuarioRepository;
import com.barber.api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository repository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto>
    login(@RequestBody LoginRequestDto request){

        Usuario usuario =
                repository.findByLogin(request.getLogin())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Usuário inválido"
                                )
                        );

        if(!usuario.getSenha()
                .equals(request.getSenha())){

            throw new RuntimeException(
                    "Senha inválida"
            );
        }

        String token =
                jwtService.gerarToken(
                        usuario.getLogin()
                );

        return ResponseEntity.ok(
                new LoginResponseDto(token)
        );
    }
}
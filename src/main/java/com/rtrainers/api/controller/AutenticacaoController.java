package com.rtrainers.api.controller;

import com.rtrainers.api.dto.LoginRequestDTO;
import com.rtrainers.api.dto.LoginResponseDTO;
import com.rtrainers.api.model.Professor;
import com.rtrainers.api.service.AutenticacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requisicao) {
        List<Professor> professores = List.of(
                new Professor(1, "Israel", "israel@rtrainers.com",
                        "$2a$10$placeholder_hash_bcrypt", true));

        LoginResponseDTO resposta = autenticacaoService.autenticar(requisicao, professores);
        return ResponseEntity.ok(resposta);
    }
}
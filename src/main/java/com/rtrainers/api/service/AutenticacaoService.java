package com.rtrainers.api.service;

import com.rtrainers.api.dto.LoginRequestDTO;
import com.rtrainers.api.dto.LoginResponseDTO;
import com.rtrainers.api.model.Professor;
import com.rtrainers.api.security.JwtServico;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutenticacaoService {

    private final JwtServico jwtServico;
    private final PasswordEncoder codificadorSenha;

    public AutenticacaoService(JwtServico jwtServico, PasswordEncoder codificadorSenha) {
        this.jwtServico = jwtServico;
        this.codificadorSenha = codificadorSenha;
    }

    public LoginResponseDTO autenticar(LoginRequestDTO requisicao, List<Professor> professores) {
        Professor professor = professores.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(requisicao.getEmail()))
                .filter(p -> Boolean.TRUE.equals(p.getAtivo()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Professor não encontrado ou inativo"));

        if (!codificadorSenha.matches(requisicao.getSenha(), professor.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        String token = jwtServico.gerarToken(professor.getEmail());
        return new LoginResponseDTO(token, professor.getNome());
    }
}
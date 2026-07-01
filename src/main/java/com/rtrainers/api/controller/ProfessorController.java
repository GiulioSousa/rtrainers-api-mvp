package com.rtrainers.api.controller;

import com.rtrainers.api.dto.AgendaDTO;
import com.rtrainers.api.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("/agenda/{nomeProfessor}")
    public ResponseEntity<List<AgendaDTO>> buscarAgenda(
            @PathVariable String nomeProfessor,
            Authentication autenticacao) throws IOException {

        List<AgendaDTO> agenda = professorService.buscarAgendaPorProfessor(nomeProfessor);
        return ResponseEntity.ok(agenda);
    }

    @PostMapping("/sessao/psr")
    public ResponseEntity<Void> registrarPsr(
            @RequestParam String nomeProfessor,
            @RequestParam String horario,
            @RequestParam String nomeAluno,
            @RequestParam String data,
            @RequestParam Integer psr,
            Authentication autenticacao) throws IOException {

        professorService.registrarPsr(nomeProfessor, horario, nomeAluno, LocalDate.parse(data), psr);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sessao/pse")
    public ResponseEntity<Void> registrarPse(
            @RequestParam String nomeProfessor,
            @RequestParam String horario,
            @RequestParam String nomeAluno,
            @RequestParam String data,
            @RequestParam Integer pse,
            Authentication autenticacao) throws IOException {

        professorService.registrarPse(nomeProfessor, horario, nomeAluno, LocalDate.parse(data), pse);
        return ResponseEntity.ok().build();
    }
}
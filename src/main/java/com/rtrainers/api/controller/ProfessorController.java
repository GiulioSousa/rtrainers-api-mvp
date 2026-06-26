package com.rtrainers.api.controller;

import com.rtrainers.api.dto.AgendaDTO;
import com.rtrainers.api.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("/agenda/{professorId}")
    public ResponseEntity<List<AgendaDTO>> buscarAgenda(
            @PathVariable Integer professorId,
            Authentication autenticacao) throws IOException {

        List<AgendaDTO> agenda = professorService.buscarAgendaPorProfessor(professorId);
        return ResponseEntity.ok(agenda);
    }

    @PostMapping("/sessao/psr")
    public ResponseEntity<Void> registrarPsr(
            @RequestParam String intervalo,
            @RequestParam Integer psr,
            Authentication autenticacao) throws IOException {

        professorService.registrarPsr(intervalo, psr);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sessao/pse")
    public ResponseEntity<Void> registrarPse(
            @RequestParam String intervalo,
            @RequestParam Integer pse,
            Authentication autenticacao) throws IOException {

        professorService.registrarPse(intervalo, pse);
        return ResponseEntity.ok().build();
    }
}
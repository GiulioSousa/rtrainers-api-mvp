package com.rtrainers.api.controller;

import com.rtrainers.api.dto.AgendaDTO;
import com.rtrainers.api.dto.AvaliacaoDTO;
import com.rtrainers.api.service.TotemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/totem")
public class TotemController {

    private final TotemService totemService;

    public TotemController(TotemService totemService) {
        this.totemService = totemService;
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<AgendaDTO>> buscarAlunosNoHorarioAtual() throws IOException {
        List<AgendaDTO> alunos = totemService.buscarAlunosNoHorarioAtual();
        return ResponseEntity.ok(alunos);
    }

    @PostMapping("/avaliacao")
    public ResponseEntity<Void> registrarAvaliacao(@RequestBody AvaliacaoDTO dto) throws IOException {
        totemService.registrarAvaliacao(dto);
        return ResponseEntity.ok().build();
    }
}
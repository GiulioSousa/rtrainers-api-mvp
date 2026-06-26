package com.rtrainers.api.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.rtrainers.api.model.Agenda;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AgendaRepository {

    private final Sheets clienteSheets;

    @Value("${google.sheets.id}")
    private String planilhaId;

    public AgendaRepository(Sheets clienteSheets) {
        this.clienteSheets = clienteSheets;
    }

    public List<Agenda> buscarTodos() throws IOException {
        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, "Agendamentos!A2:G")
                .execute();

        List<List<Object>> linhas = resposta.getValues();
        List<Agenda> agendas = new ArrayList<>();

        if (linhas == null || linhas.isEmpty()) {
            return agendas;
        }

        for (List<Object> linha : linhas) {
            Agenda agenda = new Agenda();
            agenda.setId(Integer.parseInt(linha.get(0).toString()));
            agenda.setAlunoId(Integer.parseInt(linha.get(1).toString()));
            agenda.setProfessorId(Integer.parseInt(linha.get(2).toString()));
            agenda.setTurno(linha.size() > 3 ? linha.get(3).toString() : "");
            agenda.setHorario(linha.size() > 4 ? LocalTime.parse(linha.get(4).toString()) : null);
            agenda.setDiaSemana(linha.size() > 5 ? linha.get(5).toString() : "");
            agendas.add(agenda);
        }

        return agendas;
    }

    public List<Agenda> buscarPorProfessor(Integer professorId) throws IOException {
        return buscarTodos().stream()
                .filter(agenda -> agenda.getProfessorId().equals(professorId))
                .toList();
    }

    public List<Agenda> buscarPorDiaSemana(String diaSemana) throws IOException {
        return buscarTodos().stream()
                .filter(agenda -> agenda.getDiaSemana().equalsIgnoreCase(diaSemana))
                .toList();
    }
}
package com.rtrainers.api.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.rtrainers.api.model.Agenda;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AgendaRepository {

    private final Sheets clienteSheets;

    @Value("${google.sheets.id}")
    private String planilhaId;

    @Value("${google.sheets.agendamentos.manha.aba}")
    private String abaManha;

    @Value("${google.sheets.agendamentos.manha.range}")
    private String rangeManha;

    @Value("${google.sheets.agendamentos.tarde.aba}")
    private String abaTarde;

    @Value("${google.sheets.agendamentos.tarde.range}")
    private String rangeTarde;

    public AgendaRepository(Sheets clienteSheets) {
        this.clienteSheets = clienteSheets;
    }

    public List<Agenda> buscarTodos() throws IOException {
        List<Agenda> agendas = new ArrayList<>();
        agendas.addAll(lerAba(abaManha, rangeManha, "manhã"));
        agendas.addAll(lerAba(abaTarde, rangeTarde, "tarde/noite"));
        return agendas;
    }

    private List<Agenda> lerAba(String aba, String range, String turno) throws IOException {
        String intervalo = aba + "!" + range;

        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, intervalo)
                .execute();

        List<List<Object>> linhas = resposta.getValues();
        List<Agenda> agendas = new ArrayList<>();

        if (linhas == null || linhas.isEmpty()) {
            return agendas;
        }

        String horarioAtual = "";
        String professorAtual = "";
        String estimuloAtual = "";

        for (List<Object> linha : linhas) {
            if (linha.isEmpty()) {
                continue;
            }

            String horario = obterValor(linha, 1);
            String professor = obterValor(linha, 2);
            String estimulo = obterValor(linha, 9);

            if (!horario.isBlank()) horarioAtual = horario;
            if (!professor.isBlank()) professorAtual = professor;
            if (!estimulo.isBlank()) estimuloAtual = estimulo;

            String nomeAluno = obterValor(linha, 3);
            if (nomeAluno.isBlank()) {
                continue;
            }

            Agenda agenda = new Agenda();
            agenda.setNomeAluno(nomeAluno);
            agenda.setNomeProfessor(professorAtual);
            agenda.setTurno(turno);
            agenda.setHorario(horarioAtual);
            agenda.setDiaSemana("");
            agenda.setEstimuloTreino(estimuloAtual);
            agendas.add(agenda);
        }

        return agendas;
    }

    private String obterValor(List<Object> linha, int indice) {
        if (linha.size() > indice && linha.get(indice) != null) {
            return linha.get(indice).toString().trim();
        }
        return "";
    }

    public List<Agenda> buscarPorProfessor(String nomeProfessor) throws IOException {
        return buscarTodos().stream()
                .filter(agenda -> agenda.getNomeProfessor().equalsIgnoreCase(nomeProfessor))
                .toList();
    }

    public List<Agenda> buscarPorDiaSemana(String diaSemana) throws IOException {
        return buscarTodos().stream()
                .filter(agenda -> agenda.getDiaSemana().equalsIgnoreCase(diaSemana))
                .toList();
    }
}
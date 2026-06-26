package com.rtrainers.api.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.rtrainers.api.model.Sessao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class SessaoRepository {

    private final Sheets clienteSheets;

    @Value("${google.sheets.id}")
    private String planilhaId;

    public SessaoRepository(Sheets clienteSheets) {
        this.clienteSheets = clienteSheets;
    }

    public List<Sessao> buscarTodos() throws IOException {
        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, "Agendamentos!A2:H")
                .execute();

        List<List<Object>> linhas = resposta.getValues();
        List<Sessao> sessoes = new ArrayList<>();

        if (linhas == null || linhas.isEmpty()) {
            return sessoes;
        }

        for (List<Object> linha : linhas) {
            Sessao sessao = new Sessao();
            sessao.setId(Integer.parseInt(linha.get(0).toString()));
            sessao.setAgendaId(Integer.parseInt(linha.get(1).toString()));
            sessao.setAlunoId(Integer.parseInt(linha.get(2).toString()));
            sessao.setProfessorId(Integer.parseInt(linha.get(3).toString()));
            sessao.setData(linha.size() > 4 ? LocalDate.parse(linha.get(4).toString()) : null);
            sessao.setPsr(linha.size() > 5 ? Integer.parseInt(linha.get(5).toString()) : null);
            sessao.setPse(linha.size() > 6 ? Integer.parseInt(linha.get(6).toString()) : null);
            sessoes.add(sessao);
        }

        return sessoes;
    }

    public void registrarPsr(String intervalo, Integer psr) throws IOException {
        ValueRange corpo = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(psr)));

        clienteSheets.spreadsheets().values()
                .update(planilhaId, intervalo, corpo)
                .setValueInputOption("RAW")
                .execute();
    }

    public void registrarPse(String intervalo, Integer pse) throws IOException {
        ValueRange corpo = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(pse)));

        clienteSheets.spreadsheets().values()
                .update(planilhaId, intervalo, corpo)
                .setValueInputOption("RAW")
                .execute();
    }
}
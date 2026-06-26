package com.rtrainers.api.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.rtrainers.api.model.Avaliacao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AvaliacaoRepository {

    private final Sheets clienteSheets;

    @Value("${google.sheets.id}")
    private String planilhaId;

    public AvaliacaoRepository(Sheets clienteSheets) {
        this.clienteSheets = clienteSheets;
    }

    public List<Avaliacao> buscarTodos() throws IOException {
        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, "Avaliacoes!A2:H")
                .execute();

        List<List<Object>> linhas = resposta.getValues();
        List<Avaliacao> avaliacoes = new ArrayList<>();

        if (linhas == null || linhas.isEmpty()) {
            return avaliacoes;
        }

        for (List<Object> linha : linhas) {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setId(Integer.parseInt(linha.get(0).toString()));
            avaliacao.setSessaoId(Integer.parseInt(linha.get(1).toString()));
            avaliacao.setAlunoId(Integer.parseInt(linha.get(2).toString()));
            avaliacao.setProfessorId(Integer.parseInt(linha.get(3).toString()));
            avaliacao.setNotaAula(linha.size() > 4 ? Integer.parseInt(linha.get(4).toString()) : null);
            avaliacao.setNotaProfessor(linha.size() > 5 ? Integer.parseInt(linha.get(5).toString()) : null);
            avaliacao.setTags(linha.size() > 6 ? Arrays.asList(linha.get(6).toString().split(",")) : new ArrayList<>());
            avaliacao.setData(linha.size() > 7 ? LocalDate.parse(linha.get(7).toString()) : null);
            avaliacoes.add(avaliacao);
        }

        return avaliacoes;
    }

    public boolean existeAvaliacaoNoDia(Integer alunoId, LocalDate data) throws IOException {
        return buscarTodos().stream()
                .anyMatch(a -> a.getAlunoId().equals(alunoId) && a.getData().equals(data));
    }

    public void salvar(Avaliacao avaliacao) throws IOException {
        ValueRange corpo = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(
                        "",
                        avaliacao.getSessaoId(),
                        avaliacao.getAlunoId(),
                        avaliacao.getProfessorId(),
                        avaliacao.getNotaAula(),
                        avaliacao.getNotaProfessor(),
                        String.join(",", avaliacao.getTags()),
                        avaliacao.getData().toString())));

        clienteSheets.spreadsheets().values()
                .append(planilhaId, "Avaliacoes!A:H", corpo)
                .setValueInputOption("RAW")
                .setInsertDataOption("INSERT_ROWS")
                .execute();
    }
}
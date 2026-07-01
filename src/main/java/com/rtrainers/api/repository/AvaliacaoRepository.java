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

    @Value("${google.sheets.avaliacoes.aba}")
    private String abaAvaliacao;

    @Value("${google.sheets.avaliacoes.range}")
    private String rangeAvaliacao;

    public AvaliacaoRepository(Sheets clienteSheets) {
        this.clienteSheets = clienteSheets;
    }

    public List<Avaliacao> buscarTodos() throws IOException {
        String intervalo = abaAvaliacao + "!" + rangeAvaliacao;

        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, intervalo)
                .execute();

        List<List<Object>> linhas = resposta.getValues();
        List<Avaliacao> avaliacoes = new ArrayList<>();

        if (linhas == null || linhas.isEmpty()) {
            return avaliacoes;
        }

        for (List<Object> linha : linhas) {
            if (linha.isEmpty() || linha.get(0).toString().isBlank()) {
                continue;
            }

            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setNomeAluno(linha.get(0).toString());
            avaliacao.setNomeProfessor(linha.size() > 1 ? linha.get(1).toString() : "");
            avaliacao.setNotaAula(linha.size() > 2 && !linha.get(2).toString().isBlank()
                    ? Integer.parseInt(linha.get(2).toString()) : null);
            avaliacao.setNotaProfessor(linha.size() > 3 && !linha.get(3).toString().isBlank()
                    ? Integer.parseInt(linha.get(3).toString()) : null);
            avaliacao.setTags(linha.size() > 4 && !linha.get(4).toString().isBlank()
                    ? Arrays.asList(linha.get(4).toString().split(",")) : new ArrayList<>());
            avaliacao.setData(linha.size() > 5 && !linha.get(5).toString().isBlank()
                    ? LocalDate.parse(linha.get(5).toString()) : null);
            avaliacoes.add(avaliacao);
        }

        return avaliacoes;
    }

    public boolean existeAvaliacaoNoDia(String nomeAluno, LocalDate data) throws IOException {
        return buscarTodos().stream()
                .anyMatch(a -> a.getNomeAluno().equalsIgnoreCase(nomeAluno) 
                && data.equals(a.getData()));
    }

    public void salvar(Avaliacao avaliacao) throws IOException {
        ValueRange corpo = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(
                        "",
                        avaliacao.getNomeAluno(),
                        avaliacao.getNomeProfessor(),
                        avaliacao.getNotaAula(),
                        avaliacao.getNotaProfessor(),
                        String.join(",", avaliacao.getTags()),
                        avaliacao.getData().toString())));

                        String intervaloEscrita = abaAvaliacao + "!" + rangeAvaliacao;

        clienteSheets.spreadsheets().values()
                .append(planilhaId, intervaloEscrita, corpo)
                .setValueInputOption("RAW")
                .setInsertDataOption("INSERT_ROWS")
                .execute();
    }
}
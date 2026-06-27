package com.rtrainers.api.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.rtrainers.api.model.Aluno;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoRepository {

    private final Sheets clienteSheets;

    @Value("${google.sheets.id}")
    private String planilhaId;

    @Value("${google.sheets.anamnese.aba}")
    private String abaAnamnese;
    
    @Value("${google.sheets.anamnese.range}")
    private String rangeAnamnese;

    public AlunoRepository(Sheets clienteSheets) {
        this.clienteSheets = clienteSheets;
    }

    public List<Aluno> buscarTodos() throws IOException {
        String intervalo = abaAnamnese + "!" + rangeAnamnese;

        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, intervalo)
                .execute();

        List<List<Object>> linhas = resposta.getValues();
        List<Aluno> alunos = new ArrayList<>();

        if (linhas == null || linhas.isEmpty()) {
            return alunos;
        }

        for (List<Object> linha : linhas) {
            if (linha.isEmpty() || linha.get(0).toString().isBlank()) {
                continue;
            }

            Aluno aluno = new Aluno();
            aluno.setNome(linha.get(0).toString());
            aluno.setLesao(linha.size() > 1 ? linha.get(1).toString() : "");
            aluno.setPreferencia(linha.size() > 2 ? linha.get(2).toString() : "");
            aluno.setObjetivo(linha.size() > 3 ? linha.get(3).toString() : "");
            aluno.setObservacoes(linha.size() > 4 ? linha.get(4).toString() : "");
            aluno.setEstagio(linha.size() > 5 ? linha.get(5).toString() : "");
            alunos.add(aluno);
        }

        return alunos;
    }

    public Aluno buscarPorNome(String nome) throws IOException {
        return buscarTodos().stream()
                .filter(aluno -> aluno.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElse(null);
    }
}
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

    public AlunoRepository(Sheets clienteSheets) {
        this.clienteSheets = clienteSheets;
    }

    public List<Aluno> buscarTodos() throws IOException {
        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, "ANAMNESE!A2:H")
                .execute();

        List<List<Object>> linhas = resposta.getValues();
        List<Aluno> alunos = new ArrayList<>();

        if (linhas == null || linhas.isEmpty()) {
            return alunos;
        }

        for (List<Object> linha : linhas) {
            Aluno aluno = new Aluno();
            aluno.setId(Integer.parseInt(linha.get(0).toString()));
            aluno.setNome(linha.get(1).toString());
            aluno.setEstagio(linha.size() > 2 ? linha.get(2).toString() : "");
            aluno.setLesao(linha.size() > 3 ? linha.get(3).toString() : "");
            aluno.setPreferencia(linha.size() > 4 ? linha.get(4).toString() : "");
            aluno.setObjetivo(linha.size() > 5 ? linha.get(5).toString() : "");
            aluno.setObservacoes(linha.size() > 6 ? linha.get(6).toString() : "");
            aluno.setAtivo(linha.size() > 7 ? Boolean.parseBoolean(linha.get(7).toString()) : true);
            alunos.add(aluno);
        }

        return alunos;
    }

    public Aluno buscarPorId(Integer id) throws IOException {
        return buscarTodos().stream()
                .filter(aluno -> aluno.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
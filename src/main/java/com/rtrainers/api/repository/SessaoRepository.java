package com.rtrainers.api.repository;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Repository
public class SessaoRepository {

    private final Sheets clienteSheets;

    @Value("${google.sheets.id}")
    private String planilhaId;

    @Value("${google.sheets.agendamentos.manha.aba}")
    private String abaManha;

    @Value("${google.sheets.agendamentos.tarde.aba}")
    private String abaTarde;

    public SessaoRepository(Sheets clienteSheets) {
        this.clienteSheets = clienteSheets;
    }

    public void registrarPsr(
            String nomeProfessor,
            String horario,
            String nomeAluno,
            LocalDate data,
            Integer psr) throws IOException {
        int linha = localizarLinha(nomeProfessor, horario, nomeAluno, data);
        String aba = resolverAba(horario);
        String celula = aba + "!K" + linha;
        escreverValor(celula, psr);
        preencherPresencaSeCompleto(aba, linha);
    }

    public void registrarPse(
            String nomeProfessor,
            String horario,
            String nomeAluno,
            LocalDate data,
            Integer pse) throws IOException {
        int linha = localizarLinha(nomeProfessor, horario, nomeAluno, data);
        String aba = resolverAba(horario);
        String celula = aba + "!L" + linha;
        escreverValor(celula, pse);
        preencherPresencaSeCompleto(aba, linha);
    }

    private int localizarLinha(String nomeProfessor, String horario, String nomeAluno, LocalDate data)
            throws IOException {
        String aba = resolverAba(horario);
        String intervalo = aba + "!A2:L";

        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, intervalo)
                .execute();

        List<List<Object>> linhas = resposta.getValues();

        if (linhas == null || linhas.isEmpty()) {
            throw new RuntimeException("Aba vazia ou inacessível: " + aba);
        }

        String dataAtual = "";
        String horarioAtual = "";
        String professorAtual = "";

        for (int i = 0; i < linhas.size(); i++) {
            List<Object> linha = linhas.get(i);

            String dataLinha = obterValor(linha, 0);
            String horarioLinha = obterValor(linha, 1);
            String professorLinha = obterValor(linha, 2);
            String alunoLinha = obterValor(linha, 3);

            if (!dataLinha.isBlank())
                dataAtual = dataLinha;
            if (!horarioLinha.isBlank())
                horarioAtual = horarioLinha;
            if (!professorLinha.isBlank())
                professorAtual = professorLinha;

            DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            boolean dataConfere = LocalDate.parse(dataAtual, formatadorData).equals(data);
            boolean horarioConfere = horarioAtual.equalsIgnoreCase(horario);
            boolean professorConfere = professorAtual.equalsIgnoreCase(nomeProfessor);
            boolean alunoConfere = alunoLinha.equalsIgnoreCase(nomeAluno);

            if (dataConfere && horarioConfere && professorConfere && alunoConfere) {
                return i + 2; // +2 porque dados começam na linha 2 da planilha
            }
        }

        throw new RuntimeException("Linha não encontrada para aluno: " + nomeAluno
                + ", professor: " + nomeProfessor
                + ", horário: " + horario
                + ", data: " + data);
    }

    private String resolverAba(String horario) {
        try {
            int hora = Integer.parseInt(horario.split(":")[0].replace("H", "").trim());
            return hora <= 12 ? abaManha : abaTarde;
        } catch (Exception e) {
            throw new RuntimeException("Formato de horário inválido: " + horario);
        }
    }

    private void escreverValor(String celula, Object valor) throws IOException {
        ValueRange corpo = new ValueRange()
                .setValues(Arrays.asList(Arrays.asList(valor)));

        clienteSheets.spreadsheets().values()
                .update(planilhaId, celula, corpo)
                .setValueInputOption("RAW")
                .execute();
    }

    private void preencherPresencaSeCompleto(String aba, int linhaNumero) throws IOException {
        String intervaloLinha = aba + "!K" + linhaNumero + ":L" + linhaNumero;

        ValueRange resposta = clienteSheets.spreadsheets().values()
                .get(planilhaId, intervaloLinha)
                .execute();

        List<List<Object>> valores = resposta.getValues();

        if (valores != null && !valores.isEmpty()) {
            List<Object> linha = valores.get(0);
            boolean psrPreenchido = linha.size() > 0 && !linha.get(0).toString().isBlank();
            boolean psePreenchido = linha.size() > 1 && !linha.get(1).toString().isBlank();

            if (psrPreenchido && psePreenchido) {
                String celulaPresenca = aba + "!I" + linhaNumero;
                escreverValor(celulaPresenca, "SIM");
            }
        }
    }

    private String obterValor(List<Object> linha, int indice) {
        if (linha.size() > indice && linha.get(indice) != null) {
            return linha.get(indice).toString().trim();
        }
        return "";
    }
}
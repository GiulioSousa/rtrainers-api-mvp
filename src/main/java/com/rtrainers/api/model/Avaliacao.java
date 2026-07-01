package com.rtrainers.api.model;

import java.time.LocalDate;
import java.util.List;

public class Avaliacao {

    private String nomeAluno;
    private String nomeProfessor;
    private Integer notaAula;
    private Integer notaProfessor;
    private List<String> tags;
    private LocalDate data;

    public Avaliacao() {
    }

    public Avaliacao(String nomeAluno, String nomeProfessor,
            Integer notaAula, Integer notaProfessor, List<String> tags, LocalDate data) {
        this.nomeAluno = nomeAluno;
        this.nomeProfessor = nomeProfessor;
        this.notaAula = notaAula;
        this.notaProfessor = notaProfessor;
        this.tags = tags;
        this.data = data;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public Integer getNotaAula() {
        return notaAula;
    }

    public void setNotaAula(Integer notaAula) {
        this.notaAula = notaAula;
    }

    public Integer getNotaProfessor() {
        return notaProfessor;
    }

    public void setNotaProfessor(Integer notaProfessor) {
        this.notaProfessor = notaProfessor;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}

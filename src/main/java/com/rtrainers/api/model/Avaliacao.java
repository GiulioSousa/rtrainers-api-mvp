package com.rtrainers.api.model;

import java.time.LocalDate;
import java.util.List;

public class Avaliacao {

    private Integer id;
    private Integer sessaoId;
    private Integer alunoId;
    private Integer professorId;
    private Integer notaAula;
    private Integer notaProfessor;
    private List<String> tags;
    private LocalDate data;

    public Avaliacao() {
    }

    public Avaliacao(Integer id, Integer sessaoId, Integer alunoId, Integer professorId,
            Integer notaAula, Integer notaProfessor, List<String> tags, LocalDate data) {
        this.id = id;
        this.sessaoId = sessaoId;
        this.alunoId = alunoId;
        this.professorId = professorId;
        this.notaAula = notaAula;
        this.notaProfessor = notaProfessor;
        this.tags = tags;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(Integer sessaoId) {
        this.sessaoId = sessaoId;
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Integer professorId) {
        this.professorId = professorId;
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

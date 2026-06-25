package com.rtrainers.api.dto;

import java.time.LocalDate;

public class SessaoDTO {

    private Integer id;
    private Integer agendaId;
    private Integer alunoId;
    private Integer professorId;
    private LocalDate data;
    private Integer psr;
    private Integer pse;

    public SessaoDTO() {
    }

    public SessaoDTO(Integer id, Integer agendaId, Integer alunoId,
            Integer professorId, LocalDate data, Integer psr, Integer pse) {
        this.id = id;
        this.agendaId = agendaId;
        this.alunoId = alunoId;
        this.professorId = professorId;
        this.data = data;
        this.psr = psr;
        this.pse = pse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Integer agendaId) {
        this.agendaId = agendaId;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getPsr() {
        return psr;
    }

    public void setPsr(Integer psr) {
        this.psr = psr;
    }

    public Integer getPse() {
        return pse;
    }

    public void setPse(Integer pse) {
        this.pse = pse;
    }
}
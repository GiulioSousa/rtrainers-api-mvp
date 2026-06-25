package com.rtrainers.api.model;

import java.time.LocalTime;

public class Agenda {

    private Integer id;
    private Integer alunoId;
    private Integer professorId;
    private String turno;
    private LocalTime horario;
    private String diaSemana;

    public Agenda() {
    }

    public Agenda(Integer id, Integer alunoId, Integer professorId,
            String turno, LocalTime horario, String diaSemana) {
        this.id = id;
        this.alunoId = alunoId;
        this.professorId = professorId;
        this.turno = turno;
        this.horario = horario;
        this.diaSemana = diaSemana;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
}

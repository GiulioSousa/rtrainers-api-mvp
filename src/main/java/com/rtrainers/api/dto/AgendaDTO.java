package com.rtrainers.api.dto;

import java.time.LocalTime;

public class AgendaDTO {

    private Integer id;
    private Integer alunoId;
    private Integer professorId;
    private String turno;
    private LocalTime horario;
    private String diaSemana;
    private AlunoDTO aluno;

    public AgendaDTO() {
    }

    public AgendaDTO(Integer id, Integer alunoId, Integer professorId,
            String turno, LocalTime horario, String diaSemana, AlunoDTO aluno) {
        this.id = id;
        this.alunoId = alunoId;
        this.professorId = professorId;
        this.turno = turno;
        this.horario = horario;
        this.diaSemana = diaSemana;
        this.aluno = aluno;
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

    public AlunoDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }
}
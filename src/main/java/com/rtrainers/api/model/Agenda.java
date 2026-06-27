package com.rtrainers.api.model;

public class Agenda {

    private String nomeAluno;
    private String nomeProfessor;
    private String turno;
    private String horario;
    private String diaSemana;
    private String estimuloTreino;

    public Agenda() {
    }

    public Agenda(String nomeAluno, String nomeProfessor, String turno, String horario, String diaSemana, String estimuloTreino) {
        this.nomeAluno = nomeAluno;
        this.nomeProfessor = nomeProfessor;
        this.turno = turno;
        this.horario = horario;
        this.diaSemana = diaSemana;
        this.estimuloTreino = estimuloTreino;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getEstimuloTreino() {
        return estimuloTreino;
    }

    public void setEstimuloTreino(String estimuloTreino) {
        this.estimuloTreino = estimuloTreino;
    }
}

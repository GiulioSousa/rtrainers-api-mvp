package com.rtrainers.api.dto;

public class AgendaDTO {

    private String nomeAluno;
    private String nomeProfessor;
    private String turno;
    private String horario;
    private String diaSemana;
    private String estimuloTreino;
    private AlunoDTO aluno;

    public AgendaDTO() {
    }

    public AgendaDTO(String nomeAluno, String nomeProfessor, String turno, String horario, String diaSemana, String estimuloTreino, AlunoDTO aluno)
    {
        this.nomeAluno = nomeAluno;
        this.nomeProfessor = nomeProfessor;
        this.turno = turno;
        this.horario = horario;
        this.diaSemana = diaSemana;
        this.estimuloTreino = estimuloTreino;
        this.aluno = aluno;
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

    public AlunoDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDTO aluno) {
        this.aluno = aluno;
    }
}
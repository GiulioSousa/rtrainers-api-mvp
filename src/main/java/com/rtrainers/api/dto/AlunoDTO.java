package com.rtrainers.api.dto;

public class AlunoDTO {

    private Integer id;
    private String nome;
    private String estagio;
    private String lesao;
    private String preferencia;
    private String objetivo;
    private String observacoes;

    public AlunoDTO() {
    }

    public AlunoDTO(
            Integer id,
            String nome,
            String estagio,
            String lesao,
            String preferencia,
            String objetivo,
            String observacoes) {

        this.id = id;
        this.nome = nome;
        this.estagio = estagio;
        this.lesao = lesao;
        this.preferencia = preferencia;
        this.objetivo = objetivo;
        this.observacoes = observacoes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstagio() {
        return estagio;
    }

    public void setEstagio(String estagio) {
        this.estagio = estagio;
    }

    public String getLesao() {
        return lesao;
    }

    public void setLesao(String lesao) {
        this.lesao = lesao;
    }

    public String getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
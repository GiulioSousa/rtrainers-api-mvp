package com.rtrainers.api.dto;

public class LoginResponseDTO {

    private String token;
    private String nomeProfessor;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String nomeProfessor) {
        this.token = token;
        this.nomeProfessor = nomeProfessor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }
}
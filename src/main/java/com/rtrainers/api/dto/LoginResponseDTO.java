package com.rtrainers.api.dto;

public class LoginResponseDTO {

    private String token;
    private String nomeProfesor;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String nomeProfessor) {
        this.token = token;
        this.nomeProfesor = nomeProfessor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNomeProfessor() {
        return nomeProfesor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfesor = nomeProfessor;
    }
}
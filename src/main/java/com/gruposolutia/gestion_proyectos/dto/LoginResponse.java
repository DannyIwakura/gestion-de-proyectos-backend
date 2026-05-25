package com.gruposolutia.gestion_proyectos.dto;

public class LoginResponse {

    private String token;
    private boolean requiresTotp;
    private String preToken;
    private String email;
    private String nombre;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(String token) {
        this.token = token;
        this.requiresTotp = false;
    }

    public LoginResponse(boolean requiresTotp, String preToken) {
        this.requiresTotp = requiresTotp;
        this.preToken = preToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRequiresTotp() {
        return requiresTotp;
    }

    public void setRequiresTotp(boolean requiresTotp) {
        this.requiresTotp = requiresTotp;
    }

    public String getPreToken() {
        return preToken;
    }

    public void setPreToken(String preToken) {
        this.preToken = preToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

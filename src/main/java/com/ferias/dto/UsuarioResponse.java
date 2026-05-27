package com.ferias.dto;

import java.time.LocalDateTime;

public class UsuarioResponse {

    private Integer id;
    private String nome;
    private String login;
    private LocalDateTime atualizadoEm;

    public UsuarioResponse(Integer id, String nome, String login, LocalDateTime atualizadoEm) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.atualizadoEm = atualizadoEm;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}

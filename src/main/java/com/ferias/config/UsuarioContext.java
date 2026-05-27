package com.ferias.config;

public class UsuarioContext {

    private final Integer usuarioId;
    private final String login;
    private final String nome;

    public UsuarioContext(Integer usuarioId, String login, String nome) {
        this.usuarioId = usuarioId;
        this.login = login;
        this.nome = nome;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }
}

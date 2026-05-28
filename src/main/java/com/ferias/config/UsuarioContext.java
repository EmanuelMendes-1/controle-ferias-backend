package com.ferias.config;

public class UsuarioContext {
    private final Integer usuarioId;
    private final String nome;
    private final String login;
    private final String tipo;

    public UsuarioContext(Integer usuarioId, String nome, String login, String tipo) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.login = login;
        this.tipo = tipo;
    }

    public Integer getUsuarioId() { return usuarioId; }
    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public String getTipo() { return tipo; }
}
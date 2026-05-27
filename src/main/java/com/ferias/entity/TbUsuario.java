package com.ferias.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbUsuarios", catalog = "seguranca")
public class TbUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, length = 50)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    @Column(name = "atualizado_por")
    private Integer atualizadoPor;

    @PrePersist
    @PreUpdate
    void preencherAuditoria() {
        if (atualizadoEm == null) {
            atualizadoEm = LocalDateTime.now();
        }
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Integer getAtualizadoPor() {
        return atualizadoPor;
    }

    public void setAtualizadoPor(Integer atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }
}

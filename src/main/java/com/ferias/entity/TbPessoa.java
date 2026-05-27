package com.ferias.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbPessoas", catalog = "cadastro")
public class TbPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_id")
    private Integer pessoaId;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false)
    private LocalDate nascimento;

    @Column(nullable = false, length = 20)
    private String telefone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoa_tipo_id", nullable = false)
    private TbPessoaTipo pessoaTipo;

    @Column(name = "atualizado_por", nullable = false)
    private Integer atualizadoPor;

    @Column(name = "atualizado_em", nullable = false)
    private LocalDate atualizadoEm;

    @PrePersist
    @PreUpdate
    void preencherAuditoria() {
        if (atualizadoEm == null) {
            atualizadoEm = LocalDate.now();
        }
        if (atualizadoPor == null) {
            atualizadoPor = 1;
        }
    }

    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public TbPessoaTipo getPessoaTipo() {
        return pessoaTipo;
    }

    public void setPessoaTipo(TbPessoaTipo pessoaTipo) {
        this.pessoaTipo = pessoaTipo;
    }

    public Integer getAtualizadoPor() {
        return atualizadoPor;
    }

    public void setAtualizadoPor(Integer atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }

    public LocalDate getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDate atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}

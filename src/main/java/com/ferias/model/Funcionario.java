package com.ferias.model;

import java.time.LocalDate;

public class Funcionario {

    private Integer id;
    private String nome;
    private String cpf;
    private LocalDate nascimento;
    private String telefone;
    private Integer pessoaTipoId;
    private String pessoaTipoDescricao;

    public Funcionario() {
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

    public Integer getPessoaTipoId() {
        return pessoaTipoId;
    }

    public void setPessoaTipoId(Integer pessoaTipoId) {
        this.pessoaTipoId = pessoaTipoId;
    }

    public String getPessoaTipoDescricao() {
        return pessoaTipoDescricao;
    }

    public void setPessoaTipoDescricao(String pessoaTipoDescricao) {
        this.pessoaTipoDescricao = pessoaTipoDescricao;
    }
}

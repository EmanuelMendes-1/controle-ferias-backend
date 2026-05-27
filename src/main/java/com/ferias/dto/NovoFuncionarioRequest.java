package com.ferias.dto;

import java.time.LocalDate;

public class NovoFuncionarioRequest {

    private String nome;
    private String cpf;
    private LocalDate nascimento;
    private String telefone;
    private Integer pessoaTipoId;

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
}

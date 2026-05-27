package com.ferias.dto;

public class PerfilResponse {

    private Integer id;
    private String descricao;

    public PerfilResponse(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}

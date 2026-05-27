package com.ferias.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tbPessoaTipo", catalog = "cadastro")
public class TbPessoaTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pessoa_tipo_id")
    private Integer pessoaTipoId;

    @Column(nullable = false, length = 200)
    private String descricao;

    public Integer getPessoaTipoId() {
        return pessoaTipoId;
    }

    public void setPessoaTipoId(Integer pessoaTipoId) {
        this.pessoaTipoId = pessoaTipoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

package com.ferias.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbFerias", catalog = "cadastro")
public class TbFerias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ferias_id")
    private Integer feriasId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "funcionario_id", nullable = false)
    private TbPessoa funcionario;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private boolean aprovado;

    @Column(name = "aprovado_por")
    private Integer aprovadoPor;

    @Column(name = "atualizado_por", nullable = false)
    private Integer atualizadoPor;

    @Column(name = "atualizado_em", nullable = false)
    private Integer atualizadoEm;

    @PrePersist
    @PreUpdate
    void preencherAuditoria() {
        if (atualizadoEm == null) {
            atualizadoEm = (int) (System.currentTimeMillis() / 1000);
        }
        if (atualizadoPor == null) {
            atualizadoPor = 1;
        }
    }

    public Integer getFeriasId() {
        return feriasId;
    }

    public void setFeriasId(Integer feriasId) {
        this.feriasId = feriasId;
    }

    public TbPessoa getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(TbPessoa funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Integer getAprovadoPor() {
        return aprovadoPor;
    }

    public void setAprovadoPor(Integer aprovadoPor) {
        this.aprovadoPor = aprovadoPor;
    }

    public Integer getAtualizadoPor() {
        return atualizadoPor;
    }

    public void setAtualizadoPor(Integer atualizadoPor) {
        this.atualizadoPor = atualizadoPor;
    }

    public Integer getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Integer atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}

package com.ferias.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_solicitacoes_ferias", schema = "cadastro")
public class TbSolicitacaoFerias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "funcionario_id", nullable = false)
    private Long funcionarioId;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "status", nullable = false)
    private String status = "pendente";

    @Column(name = "solicitado_em")
    private LocalDateTime solicitadoEm = LocalDateTime.now();

    @Column(name = "aprovado_por")
    private Long aprovadoPor;

    @Column(name = "observacao")
    private String observacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSolicitadoEm() { return solicitadoEm; }
    public void setSolicitadoEm(LocalDateTime solicitadoEm) { this.solicitadoEm = solicitadoEm; }

    public Long getAprovadoPor() { return aprovadoPor; }
    public void setAprovadoPor(Long aprovadoPor) { this.aprovadoPor = aprovadoPor; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
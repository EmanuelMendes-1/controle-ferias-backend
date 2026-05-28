package com.ferias.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SolicitacaoFeriasDTO {
    private Long id;
    private Long funcionarioId;
    private String funcionarioNome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status;
    private LocalDateTime solicitadoEm;
    private String observacao;

    public SolicitacaoFeriasDTO() {}

    public SolicitacaoFeriasDTO(Long id, Long funcionarioId, String funcionarioNome, 
                                LocalDate dataInicio, LocalDate dataFim, 
                                String status, LocalDateTime solicitadoEm, String observacao) {
        this.id = id;
        this.funcionarioId = funcionarioId;
        this.funcionarioNome = funcionarioNome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.solicitadoEm = solicitadoEm;
        this.observacao = observacao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFuncionarioId() { return funcionarioId; }
    public void setFuncionarioId(Long funcionarioId) { this.funcionarioId = funcionarioId; }

    public String getFuncionarioNome() { return funcionarioNome; }
    public void setFuncionarioNome(String funcionarioNome) { this.funcionarioNome = funcionarioNome; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSolicitadoEm() { return solicitadoEm; }
    public void setSolicitadoEm(LocalDateTime solicitadoEm) { this.solicitadoEm = solicitadoEm; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
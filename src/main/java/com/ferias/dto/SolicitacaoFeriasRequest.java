package com.ferias.dto;

import java.time.LocalDate;

public class SolicitacaoFeriasRequest {
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String observacao;

    public SolicitacaoFeriasRequest() {}

    public SolicitacaoFeriasRequest(LocalDate dataInicio, LocalDate dataFim, String observacao) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.observacao = observacao;
    }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
package com.ferias.dto;

public class AprovacaoRequest {
    private Long solicitacaoId;
    private String observacao;

    public AprovacaoRequest() {}

    public AprovacaoRequest(Long solicitacaoId, String observacao) {
        this.solicitacaoId = solicitacaoId;
        this.observacao = observacao;
    }

    public Long getSolicitacaoId() { return solicitacaoId; }
    public void setSolicitacaoId(Long solicitacaoId) { this.solicitacaoId = solicitacaoId; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}

package com.ferias.service;

import com.ferias.dto.SolicitacaoFeriasDTO;
import com.ferias.dto.SolicitacaoFeriasRequest;
import com.ferias.entity.TbSolicitacaoFerias;
import com.ferias.repository.jpa.SolicitacaoFeriasJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitacaoFeriasService {

    private final SolicitacaoFeriasJpaRepository solicitacaoRepository;

    public SolicitacaoFeriasService(SolicitacaoFeriasJpaRepository solicitacaoRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
    }

    private SolicitacaoFeriasDTO toDTO(TbSolicitacaoFerias entity) {
        return new SolicitacaoFeriasDTO(
            entity.getId(),
            entity.getFuncionarioId(),
            null,  // nomeFuncionario (será preenchido no controller se necessário)
            entity.getDataInicio(),
            entity.getDataFim(),
            entity.getStatus(),
            entity.getSolicitadoEm(),
            entity.getObservacao()
        );
    }

    @Transactional
    public SolicitacaoFeriasDTO criarSolicitacao(Long funcionarioId, SolicitacaoFeriasRequest request) {
        TbSolicitacaoFerias solicitacao = new TbSolicitacaoFerias();
        solicitacao.setFuncionarioId(funcionarioId);
        solicitacao.setDataInicio(request.getDataInicio());
        solicitacao.setDataFim(request.getDataFim());
        solicitacao.setObservacao(request.getObservacao());
        solicitacao.setStatus("pendente");
        
        TbSolicitacaoFerias salva = solicitacaoRepository.save(solicitacao);
        return toDTO(salva);
    }

    public List<SolicitacaoFeriasDTO> listarMinhasSolicitacoes(Long funcionarioId) {
        return solicitacaoRepository.findByFuncionarioId(funcionarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SolicitacaoFeriasDTO> listarTodasSolicitacoes() {
        return solicitacaoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<SolicitacaoFeriasDTO> listarSolicitacoesPendentes() {
        return solicitacaoRepository.findByStatus("pendente")
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public SolicitacaoFeriasDTO aprovarSolicitacao(Long solicitacaoId, Long adminId) {
        TbSolicitacaoFerias solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        solicitacao.setStatus("aprovado");
        solicitacao.setAprovadoPor(adminId);
        
        TbSolicitacaoFerias atualizada = solicitacaoRepository.save(solicitacao);
        return toDTO(atualizada);
    }

    @Transactional
    public SolicitacaoFeriasDTO recusarSolicitacao(Long solicitacaoId, Long adminId, String motivo) {
        TbSolicitacaoFerias solicitacao = solicitacaoRepository.findById(solicitacaoId)
                .orElseThrow(() -> new RuntimeException("Solicitação não encontrada"));
        
        solicitacao.setStatus("recusado");
        solicitacao.setAprovadoPor(adminId);
        if (motivo != null && !motivo.isEmpty()) {
            solicitacao.setObservacao(motivo);
        }
        
        TbSolicitacaoFerias atualizada = solicitacaoRepository.save(solicitacao);
        return toDTO(atualizada);
    }
}
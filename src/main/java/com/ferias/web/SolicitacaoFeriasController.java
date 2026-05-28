package com.ferias.web;

import com.ferias.dto.SolicitacaoFeriasDTO;
import com.ferias.dto.SolicitacaoFeriasRequest;
import com.ferias.dto.AprovacaoRequest;
import com.ferias.service.SolicitacaoFeriasService;
import com.ferias.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes-ferias")
@CrossOrigin(origins = "*")
public class SolicitacaoFeriasController {

    private final SolicitacaoFeriasService service;

    public SolicitacaoFeriasController(SolicitacaoFeriasService service) {
        this.service = service;
    }

    // Funcionário: criar solicitação
    @PostMapping
    public ResponseEntity<SolicitacaoFeriasDTO> criarSolicitacao(
            @RequestBody SolicitacaoFeriasRequest request,
            HttpServletRequest servletRequest) {
        
        Long funcionarioId = AuthUtil.usuarioId(servletRequest).longValue();
        SolicitacaoFeriasDTO solicitacao = service.criarSolicitacao(funcionarioId, request);
        return ResponseEntity.ok(solicitacao);
    }

    // Funcionário: listar minhas solicitações
    @GetMapping("/minhas")
    public ResponseEntity<List<SolicitacaoFeriasDTO>> listarMinhasSolicitacoes(
            HttpServletRequest servletRequest) {
        
        Long funcionarioId = AuthUtil.usuarioId(servletRequest).longValue();
        List<SolicitacaoFeriasDTO> solicitacoes = service.listarMinhasSolicitacoes(funcionarioId);
        return ResponseEntity.ok(solicitacoes);
    }

    // Admin: listar todas as solicitações
    @GetMapping
    public ResponseEntity<List<SolicitacaoFeriasDTO>> listarTodasSolicitacoes() {
        List<SolicitacaoFeriasDTO> solicitacoes = service.listarTodasSolicitacoes();
        return ResponseEntity.ok(solicitacoes);
    }

    // Admin: listar solicitações pendentes
    @GetMapping("/pendentes")
    public ResponseEntity<List<SolicitacaoFeriasDTO>> listarPendentes() {
        List<SolicitacaoFeriasDTO> solicitacoes = service.listarSolicitacoesPendentes();
        return ResponseEntity.ok(solicitacoes);
    }

    // Admin: aprovar solicitação
    @PutMapping("/{id}/aprovar")
    public ResponseEntity<SolicitacaoFeriasDTO> aprovarSolicitacao(
            @PathVariable Long id,
            HttpServletRequest servletRequest) {
        
        Long adminId = AuthUtil.usuarioId(servletRequest).longValue();
        SolicitacaoFeriasDTO solicitacao = service.aprovarSolicitacao(id, adminId);
        return ResponseEntity.ok(solicitacao);
    }

    // Admin: recusar solicitação
    @PutMapping("/{id}/recusar")
    public ResponseEntity<SolicitacaoFeriasDTO> recusarSolicitacao(
            @PathVariable Long id,
            @RequestBody AprovacaoRequest request,
            HttpServletRequest servletRequest) {
        
        Long adminId = AuthUtil.usuarioId(servletRequest).longValue();
        SolicitacaoFeriasDTO solicitacao = service.recusarSolicitacao(id, adminId, request.getObservacao());
        return ResponseEntity.ok(solicitacao);
    }
}
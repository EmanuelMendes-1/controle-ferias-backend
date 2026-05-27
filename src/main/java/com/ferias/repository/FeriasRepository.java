package com.ferias.repository;

import com.ferias.entity.TbFerias;
import com.ferias.entity.TbPessoa;
import com.ferias.entity.TbPessoaTipo;
import com.ferias.mapper.FeriasMapper;
import com.ferias.model.Funcionario;
import com.ferias.model.SolicitacaoFerias;
import com.ferias.model.StatusFerias;
import com.ferias.repository.jpa.TbFeriasJpaRepository;
import com.ferias.repository.jpa.TbPessoaJpaRepository;
import com.ferias.repository.jpa.TbPessoaTipoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FeriasRepository {

    private final TbPessoaJpaRepository pessoaRepo;
    private final TbPessoaTipoJpaRepository tipoRepo;
    private final TbFeriasJpaRepository feriasRepo;

    public FeriasRepository(
            TbPessoaJpaRepository pessoaRepo,
            TbPessoaTipoJpaRepository tipoRepo,
            TbFeriasJpaRepository feriasRepo) {
        this.pessoaRepo = pessoaRepo;
        this.tipoRepo = tipoRepo;
        this.feriasRepo = feriasRepo;
    }

    public List<Funcionario> listarPessoas() {
        return pessoaRepo.findAll().stream()
                .map(FeriasMapper::toFuncionario)
                .toList();
    }

    public Optional<Funcionario> buscarPessoa(Integer id) {
        return pessoaRepo.findById(id).map(FeriasMapper::toFuncionario);
    }

    public Funcionario salvarPessoa(TbPessoa pessoa) {
        return FeriasMapper.toFuncionario(pessoaRepo.save(pessoa));
    }

    public void excluirPessoa(Integer id) {
        pessoaRepo.deleteById(id);
    }

    public TbPessoaTipo buscarTipo(Integer id) {
        return tipoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de pessoa não encontrado"));
    }

    public List<SolicitacaoFerias> listarSolicitacoes() {
        return feriasRepo.findAll().stream()
                .map(FeriasMapper::toSolicitacao)
                .toList();
    }

    public Optional<SolicitacaoFerias> buscarSolicitacao(Integer id) {
        return feriasRepo.findById(id).map(FeriasMapper::toSolicitacao);
    }

    public SolicitacaoFerias salvarSolicitacao(SolicitacaoFerias solicitacao, Integer usuarioId) {
        TbPessoa funcionario = pessoaRepo.findById(solicitacao.getFuncionarioId())
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
        TbFerias entity = FeriasMapper.toEntity(solicitacao, funcionario, usuarioId);
        TbFerias salva = feriasRepo.save(entity);
        return FeriasMapper.toSolicitacao(salva);
    }

    public SolicitacaoFerias atualizarSolicitacao(SolicitacaoFerias solicitacao, Integer usuarioId) {
        TbFerias ferias = feriasRepo.findById(solicitacao.getId())
                .orElseThrow(() -> new IllegalArgumentException("Solicitação não encontrada"));
        FeriasMapper.aplicarStatus(ferias, solicitacao.getStatus(), usuarioId);
        ferias.setAtualizadoPor(usuarioId != null ? usuarioId : 1);
        ferias.setAtualizadoEm((int) (System.currentTimeMillis() / 1000));
        return FeriasMapper.toSolicitacao(feriasRepo.save(ferias));
    }
}

package com.ferias.mapper;

import com.ferias.entity.TbFerias;
import com.ferias.entity.TbPessoa;
import com.ferias.model.Funcionario;
import com.ferias.model.SolicitacaoFerias;
import com.ferias.model.StatusFerias;

import java.time.temporal.ChronoUnit;

public final class FeriasMapper {

    private FeriasMapper() {
    }

    public static Funcionario toFuncionario(TbPessoa pessoa) {
        Funcionario f = new Funcionario();
        f.setId(pessoa.getPessoaId());
        f.setNome(pessoa.getNome());
        f.setCpf(pessoa.getCpf());
        f.setNascimento(pessoa.getNascimento());
        f.setTelefone(pessoa.getTelefone());
        if (pessoa.getPessoaTipo() != null) {
            f.setPessoaTipoId(pessoa.getPessoaTipo().getPessoaTipoId());
            f.setPessoaTipoDescricao(pessoa.getPessoaTipo().getDescricao());
        }
        return f;
    }

    public static SolicitacaoFerias toSolicitacao(TbFerias ferias) {
        SolicitacaoFerias s = new SolicitacaoFerias();
        s.setId(ferias.getFeriasId());
        s.setFuncionarioId(ferias.getFuncionario().getPessoaId());
        s.setFuncionarioNome(ferias.getFuncionario().getNome());
        s.setDataInicio(ferias.getDataInicio());
        s.setDataFim(ferias.getDataFim());
        s.setDiasSolicitados(calcularDias(ferias.getDataInicio(), ferias.getDataFim()));
        s.setStatus(mapearStatus(ferias));
        return s;
    }

    public static TbFerias toEntity(SolicitacaoFerias solicitacao, TbPessoa funcionario, Integer usuarioId) {
        TbFerias ferias = new TbFerias();
        ferias.setFeriasId(solicitacao.getId());
        ferias.setFuncionario(funcionario);
        ferias.setDataInicio(solicitacao.getDataInicio());
        ferias.setDataFim(solicitacao.getDataFim());
        aplicarStatus(ferias, solicitacao.getStatus(), usuarioId);
        ferias.setAtualizadoPor(usuarioId != null ? usuarioId : 1);
        ferias.setAtualizadoEm((int) (System.currentTimeMillis() / 1000));
        return ferias;
    }

    public static StatusFerias mapearStatus(TbFerias ferias) {
        if (ferias.isAprovado()) {
            return StatusFerias.APROVADA;
        }
        if (ferias.getAprovadoPor() != null) {
            return StatusFerias.REJEITADA;
        }
        return StatusFerias.PENDENTE;
    }

    public static void aplicarStatus(TbFerias ferias, StatusFerias status, Integer usuarioId) {
        switch (status) {
            case APROVADA -> {
                ferias.setAprovado(true);
                ferias.setAprovadoPor(usuarioId);
            }
            case REJEITADA -> {
                ferias.setAprovado(false);
                ferias.setAprovadoPor(usuarioId);
            }
            default -> {
                ferias.setAprovado(false);
                ferias.setAprovadoPor(null);
            }
        }
    }

    public static int calcularDias(java.time.LocalDate inicio, java.time.LocalDate fim) {
        return (int) ChronoUnit.DAYS.between(inicio, fim) + 1;
    }
}

package com.ferias.service;

import com.ferias.dto.NovaSolicitacaoRequest;
import com.ferias.dto.NovoFuncionarioRequest;
import com.ferias.entity.TbPessoa;
import com.ferias.entity.TbPessoaTipo;
import com.ferias.model.Funcionario;
import com.ferias.model.SolicitacaoFerias;
import com.ferias.model.StatusFerias;
import com.ferias.mapper.FeriasMapper;
import com.ferias.repository.FeriasRepository;
import com.ferias.repository.jpa.TbPessoaJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeriasService {

    private final FeriasRepository repository;
    private final TbPessoaJpaRepository pessoaRepo;

    public FeriasService(FeriasRepository repository, TbPessoaJpaRepository pessoaRepo) {
        this.repository = repository;
        this.pessoaRepo = pessoaRepo;
    }

    public List<Funcionario> listarFuncionarios() {
        return repository.listarPessoas();
    }

    public Funcionario cadastrarFuncionario(NovoFuncionarioRequest request, Integer usuarioId) {
        validarPessoa(request.getNome(), request.getCpf(), request.getNascimento(), request.getTelefone(), request.getPessoaTipoId());

        if (pessoaRepo.findByCpf(normalizarCpf(request.getCpf())).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
        }

        TbPessoaTipo tipo = repository.buscarTipo(request.getPessoaTipoId());
        TbPessoa pessoa = new TbPessoa();
        pessoa.setNome(request.getNome().trim());
        pessoa.setCpf(normalizarCpf(request.getCpf()));
        pessoa.setNascimento(request.getNascimento());
        pessoa.setTelefone(request.getTelefone().trim());
        pessoa.setPessoaTipo(tipo);
        pessoa.setAtualizadoPor(usuarioId != null ? usuarioId : 1);
        pessoa.setAtualizadoEm(LocalDate.now());

        return repository.salvarPessoa(pessoa);
    }

    public Funcionario atualizarFuncionario(Integer id, NovoFuncionarioRequest request, Integer usuarioId) {
        TbPessoa pessoa = pessoaRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada"));

        validarPessoa(request.getNome(), request.getCpf(), request.getNascimento(), request.getTelefone(), request.getPessoaTipoId());

        String cpf = normalizarCpf(request.getCpf());
        pessoaRepo.findByCpf(cpf).ifPresent(outro -> {
            if (!outro.getPessoaId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado");
            }
        });

        pessoa.setNome(request.getNome().trim());
        pessoa.setCpf(cpf);
        pessoa.setNascimento(request.getNascimento());
        pessoa.setTelefone(request.getTelefone().trim());
        pessoa.setPessoaTipo(repository.buscarTipo(request.getPessoaTipoId()));
        pessoa.setAtualizadoPor(usuarioId != null ? usuarioId : 1);
        pessoa.setAtualizadoEm(LocalDate.now());

        return repository.salvarPessoa(pessoa);
    }

    public void excluirFuncionario(Integer id) {
        if (!pessoaRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada");
        }
        repository.excluirPessoa(id);
    }

    public List<SolicitacaoFerias> listarSolicitacoes() {
        return repository.listarSolicitacoes();
    }

    public SolicitacaoFerias criarSolicitacao(NovaSolicitacaoRequest request, Integer usuarioId) {
        Funcionario funcionario = repository.buscarPessoa(request.getFuncionarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));

        validarPeriodo(request.getDataInicio(), request.getDataFim());
        int dias = calcularDias(request.getDataInicio(), request.getDataFim());

        SolicitacaoFerias solicitacao = new SolicitacaoFerias();
        solicitacao.setFuncionarioId(funcionario.getId());
        solicitacao.setFuncionarioNome(funcionario.getNome());
        solicitacao.setDataInicio(request.getDataInicio());
        solicitacao.setDataFim(request.getDataFim());
        solicitacao.setDiasSolicitados(dias);
        solicitacao.setStatus(StatusFerias.PENDENTE);

        return repository.salvarSolicitacao(solicitacao, usuarioId);
    }

    public SolicitacaoFerias aprovar(Integer id, Integer usuarioId) {
        return alterarStatus(id, StatusFerias.APROVADA, usuarioId);
    }

    public SolicitacaoFerias rejeitar(Integer id, Integer usuarioId) {
        return alterarStatus(id, StatusFerias.REJEITADA, usuarioId);
    }

    private SolicitacaoFerias alterarStatus(Integer id, StatusFerias novoStatus, Integer usuarioId) {
        SolicitacaoFerias solicitacao = repository.buscarSolicitacao(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação não encontrada"));

        if (solicitacao.getStatus() != StatusFerias.PENDENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solicitação já foi processada");
        }

        solicitacao.setStatus(novoStatus);
        return repository.atualizarSolicitacao(solicitacao, usuarioId);
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datas de início e término são obrigatórias");
        }
        if (fim.isBefore(inicio)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de término deve ser igual ou posterior ao início");
        }
    }

    private void validarPessoa(String nome, String cpf, LocalDate nascimento, String telefone, Integer tipoId) {
        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é obrigatório");
        }
        if (cpf == null || cpf.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF é obrigatório");
        }
        if (nascimento == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nascimento é obrigatório");
        }
        if (telefone == null || telefone.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone é obrigatório");
        }
        if (tipoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de pessoa é obrigatório");
        }
    }

    private String normalizarCpf(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    public int calcularDias(LocalDate inicio, LocalDate fim) {
        return FeriasMapper.calcularDias(inicio, fim);
    }
}

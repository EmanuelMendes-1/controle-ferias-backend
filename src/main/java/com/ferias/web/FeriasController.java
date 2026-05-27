package com.ferias.web;

import com.ferias.dto.NovaSolicitacaoRequest;
import com.ferias.dto.NovoFuncionarioRequest;
import com.ferias.model.Funcionario;
import com.ferias.model.SolicitacaoFerias;
import com.ferias.service.FeriasService;
import com.ferias.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FeriasController {

    private final FeriasService service;

    public FeriasController(FeriasService service) {
        this.service = service;
    }

    @GetMapping("/funcionarios")
    public List<Funcionario> listarFuncionarios() {
        return service.listarFuncionarios();
    }

    @PostMapping("/funcionarios")
    public Funcionario cadastrarFuncionario(@RequestBody NovoFuncionarioRequest request, HttpServletRequest http) {
        return service.cadastrarFuncionario(request, AuthUtil.usuarioId(http));
    }

    @PutMapping("/funcionarios/{id}")
    public Funcionario atualizarFuncionario(
            @PathVariable Integer id,
            @RequestBody NovoFuncionarioRequest request,
            HttpServletRequest http) {
        return service.atualizarFuncionario(id, request, AuthUtil.usuarioId(http));
    }

    @DeleteMapping("/funcionarios/{id}")
    public void excluirFuncionario(@PathVariable Integer id) {
        service.excluirFuncionario(id);
    }

    @GetMapping("/solicitacoes")
    public List<SolicitacaoFerias> listarSolicitacoes() {
        return service.listarSolicitacoes();
    }

    @PostMapping("/solicitacoes")
    public SolicitacaoFerias criarSolicitacao(@RequestBody NovaSolicitacaoRequest request, HttpServletRequest http) {
        return service.criarSolicitacao(request, AuthUtil.usuarioId(http));
    }

    @PostMapping("/solicitacoes/{id}/aprovar")
    public SolicitacaoFerias aprovar(@PathVariable Integer id, HttpServletRequest http) {
        return service.aprovar(id, AuthUtil.usuarioId(http));
    }

    @PostMapping("/solicitacoes/{id}/rejeitar")
    public SolicitacaoFerias rejeitar(@PathVariable Integer id, HttpServletRequest http) {
        return service.rejeitar(id, AuthUtil.usuarioId(http));
    }

    @GetMapping("/calcular-dias")
    public Map<String, Integer> calcularDias(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
        return Map.of("dias", service.calcularDias(dataInicio, dataFim));
    }
}

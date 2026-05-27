package com.ferias.config;

import com.ferias.entity.TbPessoa;
import com.ferias.entity.TbPessoaTipo;
import com.ferias.entity.TbUsuario;
import com.ferias.repository.jpa.TbPessoaJpaRepository;
import com.ferias.repository.jpa.TbPessoaTipoJpaRepository;
import com.ferias.repository.jpa.TbUsuarioJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Dados mínimos para teste quando o banco está vazio.
 * Compatível com Projeto_Ferias.sql (seguranca + cadastro).
 */
@Component
public class DataBootstrap implements CommandLineRunner {

    private final TbUsuarioJpaRepository usuarioRepo;
    private final TbPessoaTipoJpaRepository tipoRepo;
    private final TbPessoaJpaRepository pessoaRepo;

    public DataBootstrap(
            TbUsuarioJpaRepository usuarioRepo,
            TbPessoaTipoJpaRepository tipoRepo,
            TbPessoaJpaRepository pessoaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.tipoRepo = tipoRepo;
        this.pessoaRepo = pessoaRepo;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepo.count() == 0) {
            TbUsuario admin = new TbUsuario();
            admin.setNome("Administrador");
            admin.setLogin("admin");
            admin.setSenha("admin123");
            admin.setAtualizadoEm(LocalDateTime.now());
            admin.setAtualizadoPor(1);
            usuarioRepo.save(admin);
        }

        TbPessoaTipo tipoFunc = tipoRepo.findByDescricao("FUNCIONARIO")
                .orElseGet(() -> {
                    TbPessoaTipo t = new TbPessoaTipo();
                    t.setDescricao("FUNCIONARIO");
                    return tipoRepo.save(t);
                });

        tipoRepo.findByDescricao("ADMIN").orElseGet(() -> {
            TbPessoaTipo t = new TbPessoaTipo();
            t.setDescricao("ADMIN");
            return tipoRepo.save(t);
        });

        if (pessoaRepo.count() == 0) {
            TbPessoa ana = new TbPessoa();
            ana.setNome("Ana Silva");
            ana.setCpf("00000000001");
            ana.setNascimento(LocalDate.of(1990, 5, 15));
            ana.setTelefone("85999990001");
            ana.setPessoaTipo(tipoFunc);
            ana.setAtualizadoPor(1);
            ana.setAtualizadoEm(LocalDate.now());
            pessoaRepo.save(ana);
        }
    }
}

package com.ferias.service;

import com.ferias.dto.PerfilRequest;
import com.ferias.dto.PerfilResponse;
import com.ferias.entity.TbPessoaTipo;
import com.ferias.repository.jpa.TbPessoaJpaRepository;
import com.ferias.repository.jpa.TbPessoaTipoJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PerfilService {

    private final TbPessoaTipoJpaRepository perfilRepo;
    private final TbPessoaJpaRepository pessoaRepo;

    public PerfilService(TbPessoaTipoJpaRepository perfilRepo, TbPessoaJpaRepository pessoaRepo) {
        this.perfilRepo = perfilRepo;
        this.pessoaRepo = pessoaRepo;
    }

    public List<PerfilResponse> listar() {
        return perfilRepo.findAll().stream()
                .map(p -> new PerfilResponse(p.getPessoaTipoId(), p.getDescricao()))
                .toList();
    }

    public PerfilResponse criar(PerfilRequest request) {
        String desc = normalizar(request.getDescricao());
        if (desc.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Descrição do perfil é obrigatória");
        }
        if (perfilRepo.findByDescricao(desc).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Perfil já existe");
        }

        TbPessoaTipo perfil = new TbPessoaTipo();
        perfil.setDescricao(desc);
        TbPessoaTipo salvo = perfilRepo.save(perfil);
        return new PerfilResponse(salvo.getPessoaTipoId(), salvo.getDescricao());
    }

    public PerfilResponse atualizar(Integer id, PerfilRequest request) {
        TbPessoaTipo perfil = perfilRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado"));

        String desc = normalizar(request.getDescricao());
        if (desc.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Descrição do perfil é obrigatória");
        }

        perfilRepo.findByDescricao(desc).ifPresent(outro -> {
            if (!outro.getPessoaTipoId().equals(perfil.getPessoaTipoId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um perfil com essa descrição");
            }
        });

        perfil.setDescricao(desc);
        TbPessoaTipo salvo = perfilRepo.save(perfil);
        return new PerfilResponse(salvo.getPessoaTipoId(), salvo.getDescricao());
    }

    public void excluir(Integer id) {
        TbPessoaTipo perfil = perfilRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado"));

        boolean emUso = pessoaRepo.findAll().stream()
                .anyMatch(p -> p.getPessoaTipo() != null && id.equals(p.getPessoaTipo().getPessoaTipoId()));
        if (emUso) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível excluir: perfil em uso");
        }

        perfilRepo.delete(perfil);
    }

    private String normalizar(String s) {
        return s == null ? "" : s.trim().toUpperCase();
    }
}

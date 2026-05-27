package com.ferias.service;

import com.ferias.dto.UsuarioRequest;
import com.ferias.dto.UsuarioResponse;
import com.ferias.entity.TbUsuario;
import com.ferias.repository.jpa.TbUsuarioJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioCrudService {

    private final TbUsuarioJpaRepository usuarioRepo;

    public UsuarioCrudService(TbUsuarioJpaRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public List<UsuarioResponse> listar() {
        return usuarioRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UsuarioResponse buscar(Integer id) {
        TbUsuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return toResponse(usuario);
    }

    public UsuarioResponse criar(UsuarioRequest request, Integer atualizadoPor) {
        String login = normalizarLogin(request.getLogin());
        String senha = request.getSenha() == null ? "" : request.getSenha();
        String nome = normalizarNome(request.getNome());

        validarObrigatorios(nome, login, senha, true);

        if (usuarioRepo.findByLogin(login).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login já existe");
        }

        TbUsuario usuario = new TbUsuario();
        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setAtualizadoEm(LocalDateTime.now());
        usuario.setAtualizadoPor(atualizadoPor);

        return toResponse(usuarioRepo.save(usuario));
    }

    public UsuarioResponse atualizar(Integer id, UsuarioRequest request, Integer atualizadoPor) {
        TbUsuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (request.getNome() != null) {
            String nome = normalizarNome(request.getNome());
            if (nome.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é obrigatório");
            }
            usuario.setNome(nome);
        }

        if (request.getLogin() != null) {
            String login = normalizarLogin(request.getLogin());
            if (login.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login é obrigatório");
            }
            if (!login.equals(usuario.getLogin()) && usuarioRepo.findByLogin(login).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login já existe");
            }
            usuario.setLogin(login);
        }

        if (request.getSenha() != null) {
            if (request.getSenha().isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha não pode ser vazia");
            }
            usuario.setSenha(request.getSenha());
        }

        usuario.setAtualizadoEm(LocalDateTime.now());
        usuario.setAtualizadoPor(atualizadoPor);

        return toResponse(usuarioRepo.save(usuario));
    }

    public void excluir(Integer id) {
        if (!usuarioRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        usuarioRepo.deleteById(id);
    }

    private UsuarioResponse toResponse(TbUsuario u) {
        return new UsuarioResponse(u.getUsuarioId(), u.getNome(), u.getLogin(), u.getAtualizadoEm());
    }

    private void validarObrigatorios(String nome, String login, String senha, boolean senhaObrigatoria) {
        if (nome.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome é obrigatório");
        }
        if (login.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login é obrigatório");
        }
        if (senhaObrigatoria && senha.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha é obrigatória");
        }
    }

    private String normalizarLogin(String s) {
        return s == null ? "" : s.trim();
    }

    private String normalizarNome(String s) {
        return s == null ? "" : s.trim();
    }
}

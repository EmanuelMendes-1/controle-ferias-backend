package com.ferias.service;

import com.ferias.config.UsuarioContext;
import com.ferias.dto.LoginRequest;
import com.ferias.dto.LoginResponse;
import com.ferias.entity.TbUsuario;
import com.ferias.repository.jpa.TbUsuarioJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {

    private final TbUsuarioJpaRepository usuarioRepo;
    private final Map<String, UsuarioContext> sessoes = new ConcurrentHashMap<>();

    public AuthService(TbUsuarioJpaRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public LoginResponse login(LoginRequest request) {
        if (request.getLogin() == null || request.getLogin().isBlank()
                || request.getSenha() == null || request.getSenha().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login e senha são obrigatórios");
        }

        String login = request.getLogin().trim();
        TbUsuario usuario = usuarioRepo.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        if (!usuario.getSenha().equals(request.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }

        String token = UUID.randomUUID().toString();
        UsuarioContext ctx = new UsuarioContext(
                usuario.getUsuarioId(),
                usuario.getLogin(),
                usuario.getNome()
        );
        sessoes.put(token, ctx);

        return new LoginResponse(token, ctx.getNome(), ctx.getLogin());
    }

    public Optional<UsuarioContext> validarToken(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(sessoes.get(token));
    }

    public void logout(String token) {
        if (token != null) {
            sessoes.remove(token);
        }
    }
}

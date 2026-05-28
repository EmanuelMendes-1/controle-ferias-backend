package com.ferias.web;

import com.ferias.config.UsuarioContext;
import com.ferias.entity.TbUsuario;
import com.ferias.repository.jpa.TbUsuarioJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static final String ATTR_USUARIO = "usuarioContext";
    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private TbUsuarioJpaRepository usuarioRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String path = request.getRequestURI();
        
        // Ignorar login, logout e OPTIONS
        if (path.contains("/api/auth/login") || 
            path.contains("/api/auth/logout") ||
            request.getMethod().equals("OPTIONS")) {
            return true;
        }
        
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token não fornecido\"}");
            return false;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());
        
        TbUsuario usuario = usuarioRepository.findByLogin(token).orElse(null);
        if (usuario == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido\"}");
            return false;
        }

        UsuarioContext contexto = new UsuarioContext(
            usuario.getUsuarioId(), 
            usuario.getLogin(), 
            usuario.getNome(), 
            usuario.getTipo() != null ? usuario.getTipo() : "FUNCIONARIO"
        );
        request.setAttribute(ATTR_USUARIO, contexto);
        return true;
    }
}
package com.ferias.web;

import com.ferias.annotation.AdminOnly;
import com.ferias.config.UsuarioContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(AdminOnly.class)) {
                UsuarioContext usuario = (UsuarioContext) request.getAttribute("usuarioContext");
                if (usuario == null || usuario.getTipo() == null || !usuario.getTipo().equals("ADMIN")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Acesso negado. Apenas administradores podem acessar este recurso.\"}");
                    return false;
                }
            }
        }
        return true;
    }
}

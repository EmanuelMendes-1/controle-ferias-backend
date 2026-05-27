package com.ferias.util;

import com.ferias.config.UsuarioContext;
import com.ferias.web.AuthInterceptor;
import jakarta.servlet.http.HttpServletRequest;

public final class AuthUtil {

    private AuthUtil() {
    }

    public static Integer usuarioId(HttpServletRequest request) {
        Object attr = request.getAttribute(AuthInterceptor.ATTR_USUARIO);
        if (attr instanceof UsuarioContext ctx) {
            return ctx.getUsuarioId();
        }
        return 1;
    }
}

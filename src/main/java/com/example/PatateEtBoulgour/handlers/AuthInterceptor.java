package com.example.PatateEtBoulgour.handlers;

import com.example.PatateEtBoulgour.annotations.RequireLogged;
import com.example.PatateEtBoulgour.annotations.RequireRole;
import com.example.PatateEtBoulgour.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            // Vérifie les controlleurs avec @Logged
            if (method.isAnnotationPresent(RequireLogged.class)) {
                if (!isAuthenticated()) {
                    response.sendRedirect("/login");
                    return false;
                }
            }

            // Vérifie les controlleurs avec @RequireRole
            RequireRole requireRole = method.getAnnotation(RequireRole.class);
            if (requireRole != null) {
                String role = requireRole.value().toUpperCase();
                if (!userService.hasRole(role)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isAuthenticated() {
        HttpSession session = request.getSession(false);
        return session != null && userService.isLoggedIn();
    }

}

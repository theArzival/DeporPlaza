package com.Sistema.DeporPlaza.config;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute
    public void addUserusuarioLogueado(Authentication authentication, Model modelo) {
        if (authentication != null && authentication.getPrincipal() instanceof UsuarioSecurity usuario) {
            modelo.addAttribute("usuarioLogueado", usuario);
        }
    }
}

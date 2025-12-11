package com.mete.eshop.configuration;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalSecurityConfig {

    @ModelAttribute
    public void addAttributes(HttpServletRequest request) {
        // This forces the CSRF token to be loaded and the session created
        // BEFORE the view starts rendering.
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if (csrfToken != null) {
            // Accessing .getToken() is what triggers the session creation
            csrfToken.getToken();
        }
    }
}
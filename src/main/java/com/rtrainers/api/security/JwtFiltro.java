package com.rtrainers.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFiltro extends OncePerRequestFilter {

    private final JwtServico jwtServico;

    public JwtFiltro(JwtServico jwtServico) {
        this.jwtServico = jwtServico;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest requisicao,
            HttpServletResponse resposta,
            FilterChain cadeiaFiltros)
            throws ServletException, IOException {

        String cabecalho = requisicao.getHeader("Authorization");

        if (cabecalho != null && cabecalho.startsWith("Bearer ")) {
            String token = cabecalho.substring(7);

            if (jwtServico.validarToken(token)) {
                String email = jwtServico.extrairEmail(token);

                UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(email, null,
                        Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(autenticacao);
            }
        }

        cadeiaFiltros.doFilter(requisicao, resposta);
    }
}
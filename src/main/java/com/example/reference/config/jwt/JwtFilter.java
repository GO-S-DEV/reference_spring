package com.example.reference.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@WebFilter
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.header}")
    private String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        if(request.getHeader(AUTHORIZATION_HEADER) == null || !request.getHeader(AUTHORIZATION_HEADER).startsWith("Bearer")) {
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        // 헤더에서 JWT 를 받아옴
        String jwt = jwtTokenProvider.resolveToken(bearerToken);

        // 유효한 토큰인지 확인
        if(jwtTokenProvider.validateToken(jwt)) {
            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(jwt));
        }

        filterChain.doFilter(request, response);

    }
}

package com.application.api.installment.security;

import com.application.api.installment.model.User;
import com.application.api.installment.exception.TokenNotValidException;
import com.application.api.installment.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token = request.getHeader(AUTHORIZATION);
            if(token != null) {
                String tokenWithoutBearer = token.replace(BEARER, "");
                String email = tokenService.validateToken(tokenWithoutBearer);
                User user = userRepository.findByEmailWithRoles(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            filterChain.doFilter(request, response);
        } catch (TokenNotValidException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    "{\n \"status\": \"" + HttpStatus.FORBIDDEN.value() + "\"," +
                            "\n \"message\": \"" + e.getLocalizedMessage() + "\"}"
            );
        }
    }
}

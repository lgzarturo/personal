package com.lgzarturo.api.personal.infrastructure.security.jwt;

import com.lgzarturo.api.personal.api.auth.AuthUserDetailsService;
import com.lgzarturo.api.personal.utils.Constants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public final JwtGenerator jwtGenerator;
    public final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtGenerator jwtGenerator, AuthUserDetailsService userDetailsService) {
        this.jwtGenerator = jwtGenerator;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authenticationHeader = request.getHeader(Constants.HEADER_AUTHENTICATION);

        if (authenticationHeader == null || !authenticationHeader.startsWith(Constants.BEARER_PREFIX)) {
            log.debug("No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authenticationHeader.replace(Constants.BEARER_PREFIX, "");
        String username = jwtGenerator.getSubject(token);

        if (username == null) {
            log.debug("Cannot find JWT token subject : {}", token);
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            log.debug("Security context already contains authentication");
            filterChain.doFilter(request, response);
            return;
        }

        var userDetails = userDetailsService.loadUserByUsername(username);
        if (!jwtGenerator.isTokenValid(token, userDetails.getUsername())) {
            log.debug("JWT token is not valid");
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("Authenticated user {}, setting security context", username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}

package org.erusakov.backend.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.erusakov.backend.config.security.authToken.JwtAuthenticationToken;
import org.erusakov.backend.controller.request.user.RefreshTokenRequest;
import org.erusakov.backend.controller.response.user.TokenResponse;
import org.erusakov.backend.service.TokenService;
import org.erusakov.backend.utils.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@NonNullApi
@RequiredArgsConstructor
public class RefreshJwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;


    private final RequestMatcher requestMatcher = new AntPathRequestMatcher(SecurityUtils.REFRESH_PATH);
    private final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (requestMatcher.matches(request)) {
            filterRefreshToken(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void filterRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RefreshTokenRequest refreshTokenRequest = objectMapper.readValue(request.getInputStream(), RefreshTokenRequest.class);
        String token = refreshTokenRequest.refreshToken();
        String username = tokenService.getUsernameFromToken(token);
        JwtAuthenticationToken jwtAuthToken = new JwtAuthenticationToken(username, token);

        try {
            authenticationManager.authenticate(jwtAuthToken);
            TokenResponse tokenResponse = tokenService.refreshTokens(username, token);
            converter.write(tokenResponse, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
        } catch (AuthenticationException authenticationException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        }
    }

}


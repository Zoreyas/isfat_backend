package org.erusakov.backend.config;

import lombok.RequiredArgsConstructor;
import org.erusakov.backend.config.security.filter.CustomBasicAuthenticationFilter;
import org.erusakov.backend.config.security.filter.JwtAuthenticationFilter;
import org.erusakov.backend.config.security.filter.RefreshJwtFilter;
import org.erusakov.backend.config.security.handler.JwtLogoutHandler;
import org.erusakov.backend.utils.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthFilter,
                                           CustomBasicAuthenticationFilter customBasicAuthFilter,
                                           RefreshJwtFilter refreshJwtFilter,
                                           JwtLogoutHandler jwtLogoutHandler) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(SecurityUtils.AUTH_PATH).permitAll()
                                .requestMatchers(SecurityUtils.REFRESH_PATH).permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/api/v1/**").authenticated()/*.permitAll()*/
                                .anyRequest().authenticated())
                .logout(logout ->
                        logout.logoutUrl(SecurityUtils.LOGOUT_PATH)
                                .addLogoutHandler(jwtLogoutHandler)
                                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).permitAll())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                                .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(refreshJwtFilter, JwtAuthenticationFilter.class)
                .addFilterAfter(customBasicAuthFilter, jwtAuthFilter.getClass());
        return http.build();
    }

}


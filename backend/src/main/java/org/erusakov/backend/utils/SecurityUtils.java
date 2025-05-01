package org.erusakov.backend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityUtils {

    public static final String AUTH_PATH = "/api/v1/auth";
    public static final String REFRESH_PATH = "/api/v1/refresh";
    public static final String LOGOUT_PATH = "/api/v1/logout";

    public static final String  BEARER_TOKEN = "Bearer";

    @Value("${security.password-encode-strength}")
    private int encodingStrength;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(encodingStrength);
    }

}

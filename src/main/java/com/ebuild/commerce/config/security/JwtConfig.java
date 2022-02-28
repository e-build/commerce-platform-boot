package com.ebuild.commerce.config.security;

import com.ebuild.commerce.oauth.token.JWTProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JWTProvider jwtProvider() {
        return new JWTProvider(secret);
    }
}

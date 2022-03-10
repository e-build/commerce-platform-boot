package com.ebuild.commerce.config.security;

import com.ebuild.commerce.config.security.properties.AppProperties;
import com.ebuild.commerce.oauth.token.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;
    private final AppProperties appProperties;

    @Bean
    public JWTProvider jwtProvider() {
        return new JWTProvider(secret, appProperties);
    }
}

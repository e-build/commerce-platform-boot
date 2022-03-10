package com.ebuild.commerce.config.security;

import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaRefreshTokenRepository;
import com.ebuild.commerce.business.auth.service.AppUserDetailsQueryService;
import com.ebuild.commerce.common.RedisService;
import com.ebuild.commerce.config.security.properties.AppProperties;
import com.ebuild.commerce.config.security.properties.CorsProperties;
import com.ebuild.commerce.oauth.exception.RestAuthenticationEntryPoint;
import com.ebuild.commerce.config.security.filter.TokenAuthenticationFilter;
import com.ebuild.commerce.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.ebuild.commerce.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.ebuild.commerce.oauth.handler.TokenAccessDeniedHandler;
import com.ebuild.commerce.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.ebuild.commerce.oauth.service.SecurityOAuth2UserService;
import com.ebuild.commerce.oauth.service.SecurityUserDetailsService;
import com.ebuild.commerce.oauth.token.JWTProvider;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final JWTProvider tokenProvider;
    private final SecurityUserDetailsService securityUserDetailsService;
    private final SecurityOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final JpaRefreshTokenRepository userRefreshTokenRepository;
    private final RedisService redisService;
    private final AppUserDetailsQueryService appUserDetailsQueryService;

    /*
    * UserDetailsService 설정
    * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .requestMatchers(PathRequest
                .toStaticResources()
                .atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler)
            .and()
                .authorizeRequests()
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/v1/auth/**", "/oauth/redirect", "/error").permitAll()
                .antMatchers("/api/**").hasAnyAuthority(RoleType.BUYER.getCode())
                .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                .anyRequest().authenticated()
            .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
            .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")
            .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /*
    * auth 매니저 설정
    * */
    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /*
    * security 설정 시, 사용할 인코더 설정
    * */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    * 토큰 필터 설정
    * */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider, redisService, appUserDetailsQueryService);
    }

    /*
    * 쿠키 기반 인가 Repository
    * 인가 응답을 연계 하고 검증할 때 사용.
    * */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    /*
    * Oauth 인증 성공 핸들러
    * */
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    /*
     * Oauth 인증 실패 핸들러
     * */
    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    /*
    * Cors 설정
    * */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}

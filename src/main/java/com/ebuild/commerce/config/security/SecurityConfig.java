package com.ebuild.commerce.config.security;

import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaRefreshTokenRepository;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.filter.TokenAuthenticationFilter;
import com.ebuild.commerce.config.security.properties.AppProperties;
import com.ebuild.commerce.config.security.properties.CorsProperties;
import com.ebuild.commerce.oauth.exception.RestAuthenticationEntryPoint;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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

@EnableConfigurationProperties({
    CorsProperties.class,
    AppProperties.class
})
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String[] PERMIT_ALL_URI_ARRAY = {"/api/v1/auth/**", "/oauth/redirect", "/error", "/test/**"};

  private final CorsProperties corsProperties;
  private final AppProperties appProperties;
  private final JWTProvider tokenProvider;
  private final SecurityUserDetailsService securityUserDetailsService;
  private final SecurityOAuth2UserService oAuth2UserService;
  private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
  private final JpaRefreshTokenRepository userRefreshTokenRepository;
  private final JsonHelper jsonHelper;

  /*
   * UserDetailsService ??????
   * */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(securityUserDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .requestMatchers(PathRequest
            .toStaticResources()
            .atCommonLocations()
        );
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
        .authenticationEntryPoint(new RestAuthenticationEntryPoint(jsonHelper))
        .accessDeniedHandler(tokenAccessDeniedHandler)
        .and()
        .authorizeRequests()
        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
        .antMatchers(PERMIT_ALL_URI_ARRAY).permitAll()
        .antMatchers(HttpMethod.POST, "/api/v1/buyers", "/api/v1/sellers").permitAll()
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
   * auth ????????? ??????
   * */
  @Override
  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  /*
   * security ?????? ???, ????????? ????????? ??????
   * */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /*
   * ?????? ?????? ??????
   * */
  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter(tokenProvider, jsonHelper);
  }

  /*
   * ?????? ?????? ?????? Repository
   * ?????? ????????? ?????? ?????? ????????? ??? ??????.
   * */
  @Bean
  public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
    return new OAuth2AuthorizationRequestBasedOnCookieRepository();
  }

  /*
   * Oauth ?????? ?????? ?????????
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
   * Oauth ?????? ?????? ?????????
   * */
  @Bean
  public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
    return new OAuth2AuthenticationFailureHandler(
        oAuth2AuthorizationRequestBasedOnCookieRepository());
  }

  /*
   * Cors ??????
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

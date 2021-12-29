package com.ebuild.commerce.config.security;

import com.ebuild.commerce.config.filter.JwtAuthFilter;
import com.ebuild.commerce.config.security.jwt.JwtAuthTokenProvider;
import com.ebuild.commerce.exception.security.JwtAccessDeniedHandler;
import com.ebuild.commerce.exception.security.JwtAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthTokenProvider jwtAuthTokenProvider;
  private final JwtAuthEntryPoint jwtAuthEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제
        .csrf().disable() // csrf 보안 토큰 disable 처리

        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler)

        // TODO: 뭔지 확인 필요...?
        .and()
        .headers()
        .frameOptions()
        .sameOrigin()

        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //  세션 기반 인증 비활성화

        .and()
        .authorizeRequests() // 요청에 대한 사용권한 체크
        .antMatchers(
              "/api/v1/users/signup"
            , "/api/v1/users/authenticate"
                    ).permitAll()
        .anyRequest().authenticated()

        .and()
        .addFilterBefore(new JwtAuthFilter(jwtAuthTokenProvider), UsernamePasswordAuthenticationFilter.class)
        ;

  }

}

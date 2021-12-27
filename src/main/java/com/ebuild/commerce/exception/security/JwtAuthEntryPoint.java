package com.ebuild.commerce.exception.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@RequiredArgsConstructor
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  private final HandlerExceptionResolver handlerExceptionResolver;

  /**
   * Commences an authentication scheme.
   * <p>
   * <code>ExceptionTranslationFilter</code> will populate the <code>HttpSession</code>
   * attribute named
   * <code>AbstractAuthenticationProcessingFilter.SPRING_SECURITY_SAVED_REQUEST_KEY</code>
   * with the requested target URL before calling this method.
   * <p>
   * Implementations should modify the headers on the <code>ServletResponse</code> as necessary to
   * commence the authentication process.
   *
   * @param request       that resulted in an <code>AuthenticationException</code>
   * @param response      so that the user agent can begin authentication
   * @param authException that caused the invocation
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    handlerExceptionResolver.resolveException(request, response, null, authException);
  }
}

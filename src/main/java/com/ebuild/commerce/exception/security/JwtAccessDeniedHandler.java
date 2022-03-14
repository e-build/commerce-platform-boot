package com.ebuild.commerce.exception.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  private final HandlerExceptionResolver handlerExceptionResolver;
  /**
   * Handles an access denied failure.
   *
   * @param request               that resulted in an <code>AccessDeniedException</code>
   * @param response              so that the user agent can be advised of the failure
   * @param accessDeniedException that caused the invocation
   * @throws IOException      in the event of an IOException
   * @throws ServletException in the event of a ServletException
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    log.error("Access eenied exception : {}", accessDeniedException.getMessage());
    handlerExceptionResolver.resolveException(request, response, null, accessDeniedException);
  }
}

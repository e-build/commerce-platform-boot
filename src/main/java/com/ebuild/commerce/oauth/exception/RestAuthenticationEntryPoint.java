package com.ebuild.commerce.oauth.exception;

import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.exception.security.UnauthorizedException;
import com.ebuild.commerce.util.HttpUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final JsonHelper jsonHelper;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException
  ) throws IOException, ServletException {
    log.error("{}, {}, {}",
        authException.getMessage(), authException.getLocalizedMessage(), authException.getCause());

    if (authException instanceof InsufficientAuthenticationException) {
      authException = new UnauthorizedException();
    }

    HttpUtils.jsonFlush(
        response,
        HttpServletResponse.SC_UNAUTHORIZED,
        jsonHelper.serialize(CommonResponse.ERROR(authException))
    );
  }
}

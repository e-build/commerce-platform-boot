package com.ebuild.commerce.exception.security;

import com.ebuild.commerce.exception.CommerceException;
import com.ebuild.commerce.exception.CommerceExceptionCode;
import org.springframework.security.core.AuthenticationException;

public class UnauthorizedException extends AuthenticationException implements CommerceException {

  @Override
  public String getCode() {
    return CommerceExceptionCode.UN_AUTHORIZED_EXCEPTION.getCode();
  }

  public UnauthorizedException() {
    super(CommerceExceptionCode.UN_AUTHORIZED_EXCEPTION.getDetail());
  }
}

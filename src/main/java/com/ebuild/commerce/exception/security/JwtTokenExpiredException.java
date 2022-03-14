package com.ebuild.commerce.exception.security;

import com.ebuild.commerce.exception.CommerceException;
import com.ebuild.commerce.exception.CommerceExceptionCode;

public class JwtTokenExpiredException extends RuntimeException implements CommerceException {

  @Override
  public String getCode() {
    return CommerceExceptionCode.JWT_EXPIRED_EXCEPTION.getCode();
  }

  public JwtTokenExpiredException() {
    super(CommerceExceptionCode.JWT_EXPIRED_EXCEPTION.getDetail());
  }
}

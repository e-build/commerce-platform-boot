package com.ebuild.commerce.exception.security;

import com.ebuild.commerce.exception.CommerceException;
import com.ebuild.commerce.exception.CommerceExceptionCode;

public class JwtTokenInvalidException extends RuntimeException implements CommerceException {

  @Override
  public String getCode() {
    return CommerceExceptionCode.JWT_INVALID_EXCEPTION.getCode();
  }

  public JwtTokenInvalidException(String message) {
    super(message);
  }
}

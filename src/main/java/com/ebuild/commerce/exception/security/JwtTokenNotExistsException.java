package com.ebuild.commerce.exception.security;

import com.ebuild.commerce.exception.CommerceException;
import com.ebuild.commerce.exception.CommerceExceptionCode;

public class JwtTokenNotExistsException extends RuntimeException implements CommerceException {

  @Override
  public String getCode() {
    return CommerceExceptionCode.JWT_NOT_EXISTS_EXCEPTION.getCode();
  }

  public JwtTokenNotExistsException() {
    super(CommerceExceptionCode.JWT_NOT_EXISTS_EXCEPTION.getDetail());
  }
}

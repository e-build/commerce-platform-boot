package com.ebuild.commerce.exception.security;

import com.ebuild.commerce.business.auth.domain.ReAuthReasonType;
import com.ebuild.commerce.exception.CommerceException;
import com.ebuild.commerce.exception.CommerceExceptionCode;

public class ReAuthenticateRequiredException extends RuntimeException implements CommerceException {

  @Override
  public String getCode() {
    return CommerceExceptionCode.RE_AUTHENTICATE_REQUIRED_EXCEPTION.getCode();
  }

  public ReAuthenticateRequiredException(ReAuthReasonType reAuthReasonType) {
    super(String.format(CommerceExceptionCode.RE_AUTHENTICATE_REQUIRED_EXCEPTION.getDetail(), reAuthReasonType.getDetail()));
  }
}

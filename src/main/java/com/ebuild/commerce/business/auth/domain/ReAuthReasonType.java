package com.ebuild.commerce.business.auth.domain;

import lombok.Getter;

@Getter
public enum ReAuthReasonType {
  NO_TOKEN_STORED("저장된 리프레시 토큰이 없습니다."),
  EXPIRED_REFRESH_TOKEN("리프레시 토큰이 만료되었습니다."),
  TOKENS_DO_NOT_MATCH("리프레시 토큰이 저장된 토큰과 일치하지 않습니다."),
  INVALID_TOKEN("토큰이 유효한 문자열이 아닙니다.")
  ;

  private final String detail;

  ReAuthReasonType(String detail) {
    this.detail = detail;
  }
}

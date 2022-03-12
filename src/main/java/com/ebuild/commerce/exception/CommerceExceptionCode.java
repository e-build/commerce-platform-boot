package com.ebuild.commerce.exception;

import com.ebuild.commerce.exception.security.JwtTokenInvalidException;
import lombok.Getter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public enum CommerceExceptionCode {

  NOT_FOUND_EXCEPTION(NotFoundException.class, "ERR-001-001", "%s은 존재하지 않는 %s입니다."),
  ALREADY_EXIST_EXCEPTION(AlreadyExistsException.class, "ERR-001-002", "%s은 이미 존재하는 %s입니다."),
  INVALID_PASSWORD_EXCEPTION(BadCredentialsException.class, "ERR-001-003", "이메일과 패스워드가 일치하지 않습니다."),
  METHOD_ARGUMENT_NOT_VALID_EXCEPTION(MethodArgumentNotValidException.class, "ERR-001-004", "이메일과 패스워드가 일치하지 않습니다."),
  JWT_INVALID_EXCEPTION(JwtTokenInvalidException.class, "ERR-001-005", "JWT 토큰이 유효하지 않습니다."),
  ;

  private final Class<? extends Exception> exceptionClass;
  private final String code;
  private final String detail;

  CommerceExceptionCode(Class<? extends Exception> exceptionClass, String code, String detail) {
    this.exceptionClass = exceptionClass;
    this.code = code;
    this.detail = detail;
  }
}
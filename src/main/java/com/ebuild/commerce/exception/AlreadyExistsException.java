package com.ebuild.commerce.exception;

public class AlreadyExistsException extends RuntimeException implements CommerceException{

  @Override
  public String getCode() {
    return CommerceExceptionCode.ALREADY_EXIST_EXCEPTION.getCode();
  }

  public AlreadyExistsException(String contents, String type) {
    super(String.format(CommerceExceptionCode.ALREADY_EXIST_EXCEPTION.getDetail(), type));
  }
}

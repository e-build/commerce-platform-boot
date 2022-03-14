package com.ebuild.commerce.exception;

public class AlreadyExistsException extends RuntimeException{

  public AlreadyExistsException(String contents, String type) {
    super(String.format(CommerceExceptionCode.ALREADY_EXIST_EXCEPTION.getDetail(), type));
  }
}

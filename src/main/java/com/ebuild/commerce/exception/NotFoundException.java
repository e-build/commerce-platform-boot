package com.ebuild.commerce.exception;

public class NotFoundException extends RuntimeException{

  public NotFoundException(String message) {
    super(String.format(CommerceExceptionCode.NOT_FOUND_EXCEPTION.getDetail(), message));
  }
}

package com.ebuild.commerce.exception;

public class NotFoundException extends RuntimeException{

  public NotFoundException(String id, String type) {
    super(String.format(CommerceExceptionCode.NOT_FOUND_EXCEPTION.getDetail(), id, type));
  }
}

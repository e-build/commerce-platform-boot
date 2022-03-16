package com.ebuild.commerce.exception;

public class PagingDoNotHaveTotalCountException extends RuntimeException implements CommerceException{

  @Override
  public String getCode() {
    return CommerceExceptionCode.PAGING_DO_NOT_HAVE_TOTAL_COUNT_EXCEPTION.getCode();
  }

  public PagingDoNotHaveTotalCountException() {
    super(CommerceExceptionCode.PAGING_DO_NOT_HAVE_TOTAL_COUNT_EXCEPTION.getDetail());
  }
}

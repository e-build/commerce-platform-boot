package com.ebuild.commerce.config.security.jwt;

public interface AuthToken<T> {
  boolean validate();
  T getData();
}

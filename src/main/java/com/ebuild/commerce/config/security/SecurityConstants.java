package com.ebuild.commerce.config.security;

public class SecurityConstants {

  public static final String JWT_TOKEN_PREFIX = "Bearer ";
  public static final String ACCESS_TOKEN_HEADER = "X-Access-Token";
  public static final String REFRESH_TOKEN_HEADER = "X-Refresh-Token";
  public static final String JWT_AUTHORITIES_KEY = "ROLE";
  public static final String OAUTH_LOGIN_SUCCESS_PREFIX = "OAUTH_LOGIN_SUCCESS_";

  public static final String REDIS_REFRESH_TOKEN_KEY = "REFRESH_TOKEN : ";

}

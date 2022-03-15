package com.ebuild.commerce.util;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.ebuild.commerce.config.security.SecurityConstants;
import javax.servlet.http.HttpServletRequest;

public class HeaderUtil {

  public static String getAccessToken(HttpServletRequest request) {
    return tokenValue(request.getHeader(SecurityConstants.ACCESS_TOKEN_HEADER));
  }

  public static String getRefreshToken(HttpServletRequest request) {
    return tokenValue(request.getHeader(SecurityConstants.REFRESH_TOKEN_HEADER));
  }

  private static String tokenValue(String accessTokenString) {
    if (isBlank(accessTokenString))
      return null;

    if (!accessTokenString.startsWith(SecurityConstants.JWT_TOKEN_PREFIX))
      return null;

    return accessTokenString.substring(SecurityConstants.JWT_TOKEN_PREFIX.length());
  }


}

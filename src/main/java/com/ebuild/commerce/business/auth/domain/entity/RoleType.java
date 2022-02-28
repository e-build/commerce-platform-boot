package com.ebuild.commerce.business.auth.domain.entity;

import java.util.Arrays;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum RoleType {
  ADMIN("ROLE_ADMIN", "관리자"),
  BUYER("ROLE_BUYER", "구매자"),
  SELLER("ROLE_SELLER", "판매자"),
  ANONYMOUS("ANONYMOUS", "익명");

  private String code;
  private String display;

  RoleType(String code, String display) {
    this.code = code;
    this.display = display;
  }

  public static RoleType of(String code) {
    return Arrays.stream(RoleType.values())
        .filter(r -> StringUtils.equals(r.getCode(), code))
        .findAny()
        .orElse(ANONYMOUS);
  }
}

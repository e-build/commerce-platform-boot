package com.ebuild.commerce.business.user.role;

import java.util.Arrays;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum CommerceRole {
  ADMIN("ROLE_ADMIN", "관리자 권한"),
  BUYER("ROLE_BUYER", "구매자 권한"),
  SELLER("ROLE_SELLER", "판매자 권한"),
  ANONYMOUS("ANONYMOUS", "익명");

  private String code;
  private String description;

  CommerceRole(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static CommerceRole of(String code) {
    return Arrays.stream(CommerceRole.values())
        .filter(r -> StringUtils.equals(r.getCode(), code))
        .findAny()
        .orElse(ANONYMOUS);
  }
}

package com.ebuild.commerce.business.auth.domain.dto;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserSaveResDto {

  private Long id;
  private String email;
  private String nickname;
  private String phoneNumber;
  private List<String> roles;

  public AppUserSaveResDto(AppUserDetails entity) {
    this.email = entity.getEmail();
    this.nickname = entity.getNickname();
    this.phoneNumber = entity.getPhoneNumber();
    this.roles = entity.getRoleList()
        .stream()
        .map(r -> r.getRole().getName().getCode())
        .collect(Collectors.toList());
  }

}

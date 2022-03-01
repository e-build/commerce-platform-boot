package com.ebuild.commerce.business.auth.domain.dto;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class AppUserDetailsSaveReqDto {

  private Long id;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  private String password;
  @NotBlank
  private String nickname;
  @NotBlank
  private String phoneNumber;
  @Setter
  private Role[] roles;

  public AppUserDetails toEntity() {
    AppUserDetails appUserDetails = AppUserDetails.builder()
        .email(this.email)
        .password(this.password)
        .nickname(this.nickname)
        .phoneNumber(this.phoneNumber)
        .build();
    appUserDetails.addRoles(roles);
    return appUserDetails;
  }

  public void encryptPassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(password);
  }
}

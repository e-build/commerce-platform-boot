package com.ebuild.commerce.business.user.commerceUserDetail.domain.dto;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.role.domain.Role;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class CommerceUserSaveReqDto {

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

  public CommerceUserDetail toEntity() {
    return CommerceUserDetail.builder()
        .email(this.email)
        .password(this.password)
        .nickname(this.nickname)
        .phoneNumber(this.phoneNumber)
        .roles(roles)
        .build();
  }

  public void encryptPassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(password);
  }
}

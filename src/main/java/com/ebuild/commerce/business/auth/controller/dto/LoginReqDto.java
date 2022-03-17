package com.ebuild.commerce.business.auth.controller.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {

  @Email
  @NotBlank
  private String username;
  @NotBlank
  private String password;

}

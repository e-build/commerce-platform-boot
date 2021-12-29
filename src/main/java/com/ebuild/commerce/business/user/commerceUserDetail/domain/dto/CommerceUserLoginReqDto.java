package com.ebuild.commerce.business.user.commerceUserDetail.domain.dto;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommerceUserLoginReqDto {

  @Valid
  private LoginReqParam loginAccount;

  public String getUsername(){
    return this.loginAccount.username;
  };

  public String getPassword(){
    return this.loginAccount.password;
  };

  @Getter
  private static class LoginReqParam {
    @Email
    private String username;
    @NotBlank
    private String password;
  }

}

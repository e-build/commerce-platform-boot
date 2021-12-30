package com.ebuild.commerce.business.user.commerceUserDetail.domain.dto;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.role.domain.Role;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class CommerceUserSaveResDto {

  private Long id;
  private String email;
  private String nickname;
  private String phoneNumber;
  private List<String> roles;

  public CommerceUserSaveResDto(CommerceUserDetail entity) {
    this.id = entity.getId();
    this.email = entity.getEmail();
    this.nickname = entity.getNickname();
    this.phoneNumber = entity.getPhoneNumber();
    this.roles = entity.getRoleList()
        .stream()
        .map(r -> r.getRole().getName().getCode())
        .collect(Collectors.toList());
  }

}

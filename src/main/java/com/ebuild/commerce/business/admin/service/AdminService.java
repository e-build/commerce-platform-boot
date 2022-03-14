package com.ebuild.commerce.business.admin.service;

import com.ebuild.commerce.business.admin.domain.dto.AdminSaveReqDto;
import com.ebuild.commerce.business.admin.domain.dto.AdminSaveResDto;
import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.admin.repository.JpaAdminRepository;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.google.common.collect.Lists;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final PasswordEncoder passwordEncoder;
  private final AdminQueryService adminQueryService;
  private final JpaRoleRepository jpaRoleRepository;
  private final JpaAdminRepository jpaSellerRepository;

  public AdminSaveResDto signup(AdminSaveReqDto adminSaveReqDto) {
    String email = adminSaveReqDto.getCommerceUser().getEmail();
    Admin existUser = adminQueryService.findByEmail(email);
    if (!Objects.isNull(existUser))
      throw new AlreadyExistsException(email, "이메일");

    // 권한 부여
    adminSaveReqDto.getCommerceUser()
        .setRoles(
            jpaRoleRepository
                .findAllByNameIn(Lists.newArrayList(RoleType.ADMIN, RoleType.BUYER))
                .toArray(new Role[0])
        );

    // 패스워드 암호화
    adminSaveReqDto.getCommerceUser().encryptPassword(passwordEncoder);

    Admin admin = Admin.builder()
        .appUserDetails(adminSaveReqDto.getCommerceUser().toEntity())
        .build();

    jpaSellerRepository.save(admin);

    return AdminSaveResDto.builder()
        .admin(admin)
        .build();
  }

}

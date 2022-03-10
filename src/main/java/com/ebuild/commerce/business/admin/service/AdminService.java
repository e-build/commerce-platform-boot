package com.ebuild.commerce.business.admin.service;

import com.ebuild.commerce.business.admin.domain.dto.AdminSaveReqDto;
import com.ebuild.commerce.business.admin.domain.dto.AdminSaveResDto;
import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.admin.repository.JpaAdminRepository;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.google.common.collect.Lists;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final PasswordEncoder passwordEncoder;
  private final JpaAppUserDetailsRepository jpaAppUserDetailsRepository;
  private final JpaRoleRepository jpaRoleRepository;
  private final JpaAdminRepository jpaSellerRepository;

  public AdminSaveResDto signup(AdminSaveReqDto adminSaveReqDto) {
    findCommerceUserByEmail(adminSaveReqDto.getCommerceUser().getEmail())
        .ifPresent(user -> {
            throw new AlreadyExistsException("이미 다른 계정에서 사용중인 email 입니다.");
        });

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

  private Optional<AppUserDetails> findCommerceUserByEmail(String email){
    return jpaAppUserDetailsRepository.findOneByEmail(email);
  }
}

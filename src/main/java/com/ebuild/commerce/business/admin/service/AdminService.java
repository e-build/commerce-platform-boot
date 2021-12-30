package com.ebuild.commerce.business.admin.service;

import com.ebuild.commerce.business.admin.domain.dto.AdminSaveReqDto;
import com.ebuild.commerce.business.admin.domain.dto.AdminSaveResDto;
import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.admin.repository.JpaAdminRepository;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import com.ebuild.commerce.business.user.role.CommerceRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import com.ebuild.commerce.business.user.role.repository.JpaRoleRepository;
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
  private final CommerceUserDetailRepository jpaCommerceUserDetailRepository;
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
                .findAllByNameIn(Lists.newArrayList(CommerceRole.ADMIN, CommerceRole.BUYER))
                .toArray(new Role[0])
        );

    // 패스워드 암호화
    adminSaveReqDto.getCommerceUser().encryptPassword(passwordEncoder);

    Admin admin = Admin.builder()
        .commerceUserDetail(adminSaveReqDto.getCommerceUser().toEntity())
        .build();

    jpaSellerRepository.save(admin);

    return AdminSaveResDto.builder()
        .admin(admin)
        .build();
  }

  private Optional<CommerceUserDetail> findCommerceUserByEmail(String email){
    return jpaCommerceUserDetailRepository.findOneByEmail(email);
  }
}

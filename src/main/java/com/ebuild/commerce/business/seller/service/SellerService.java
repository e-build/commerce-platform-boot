package com.ebuild.commerce.business.seller.service;

import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.business.seller.controller.dto.SellerSaveReqDto;
import com.ebuild.commerce.business.seller.controller.dto.SellerSaveResDto;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.business.seller.repository.JpaSellerRepository;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.ebuild.commerce.exception.NotFoundException;
import com.google.common.collect.Lists;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SellerService {

  private final PasswordEncoder passwordEncoder;
  private final JpaRoleRepository jpaRoleRepository;
  private final JpaSellerRepository jpaSellerRepository;
  private final JpaCompanyRepository jpaCompanyRepository;
  private final SellerQueryService sellerQueryService;


  public SellerSaveResDto signup(SellerSaveReqDto sellerSaveReqDto) {
    String email = sellerSaveReqDto.getAppUserDetails().getEmail();
    Seller existUser = sellerQueryService.findByEmail(email);
    if (!Objects.isNull(existUser)) {
      throw new AlreadyExistsException(email, "이메일");
    }

    // 권한 부여
    sellerSaveReqDto.getAppUserDetails()
        .setRoles(
            jpaRoleRepository
                .findAllByNameIn(Lists.newArrayList(RoleType.SELLER))
                .toArray(new Role[0])
        );

    // 패스워드 암호화
    sellerSaveReqDto.getAppUserDetails().encryptPassword(passwordEncoder);

    Long companyId = sellerSaveReqDto.getCompanyId();

    Seller seller = Seller.builder()
        .appUserDetails(sellerSaveReqDto.getAppUserDetails().toEntity())
        .shippingAddress(sellerSaveReqDto.getShippingAddress().get())
        .company(jpaCompanyRepository
            .findById(companyId)
            .orElseThrow(() -> new NotFoundException(String.valueOf(companyId), "회사"))
        ).build();

    jpaSellerRepository.save(seller);

    return SellerSaveResDto.builder()
        .seller(seller)
        .build();
  }

}

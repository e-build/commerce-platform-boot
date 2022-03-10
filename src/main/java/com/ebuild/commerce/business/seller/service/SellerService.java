package com.ebuild.commerce.business.seller.service;

import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.seller.domain.dto.SellerSaveReqDto;
import com.ebuild.commerce.business.seller.domain.dto.SellerSaveResDto;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.business.seller.repository.JpaSellerRepository;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.ebuild.commerce.exception.NotFoundException;
import com.google.common.collect.Lists;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SellerService {

  private final PasswordEncoder passwordEncoder;
  private final JpaAppUserDetailsRepository jpaAppUserDetailsRepository;
  private final JpaRoleRepository jpaRoleRepository;
  private final JpaSellerRepository jpaSellerRepository;
  private final JpaCompanyRepository jpaCompanyRepository;

  public SellerSaveResDto signup(SellerSaveReqDto sellerSaveReqDto) {
    findCommerceUserByEmail(sellerSaveReqDto.getAppUserDetails().getEmail()).ifPresent(user -> {
      throw new AlreadyExistsException("이미 다른 계정에서 사용중인 email 입니다.");
    });

    // 권한 부여
    sellerSaveReqDto.getAppUserDetails()
        .setRoles(
            jpaRoleRepository
                .findAllByNameIn(Lists.newArrayList(RoleType.SELLER))
                .toArray(new Role[0])
        );

    // 패스워드 암호화
    sellerSaveReqDto.getAppUserDetails().encryptPassword(passwordEncoder);

    Seller seller = Seller.builder()
        .appUserDetails(sellerSaveReqDto.getAppUserDetails().toEntity())
        .shippingAddress(sellerSaveReqDto.getShippingAddress().get())
        .company(jpaCompanyRepository
            .findById(sellerSaveReqDto.getCompanyId())
            .orElseThrow(()-> new NotFoundException("회사를 찾을 수 없습니다. companyId : ["+sellerSaveReqDto.getCompanyId()+"]")))
        .build();

    jpaSellerRepository.save(seller);

    return SellerSaveResDto.builder()
        .seller(seller)
        .build();
  }

  private Optional<AppUserDetails> findCommerceUserByEmail(String email){
    return jpaAppUserDetailsRepository.findOneByEmail(email);
  }
}

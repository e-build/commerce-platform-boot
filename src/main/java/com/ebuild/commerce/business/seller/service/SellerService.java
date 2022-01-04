package com.ebuild.commerce.business.seller.service;

import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import com.ebuild.commerce.business.user.role.CommerceRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import com.ebuild.commerce.business.user.role.repository.JpaRoleRepository;
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
  private final CommerceUserDetailRepository jpaCommerceUserDetailRepository;
  private final JpaRoleRepository jpaRoleRepository;
  private final JpaSellerRepository jpaSellerRepository;
  private final JpaCompanyRepository jpaCompanyRepository;

  public SellerSaveResDto signup(SellerSaveReqDto sellerSaveReqDto) {
    findCommerceUserByEmail(sellerSaveReqDto.getCommerceUser().getEmail()).ifPresent(user -> {
      throw new AlreadyExistsException("이미 다른 계정에서 사용중인 email 입니다.");
    });

    // 권한 부여
    sellerSaveReqDto.getCommerceUser()
        .setRoles(
            jpaRoleRepository
                .findAllByNameIn(Lists.newArrayList(CommerceRole.SELLER))
                .toArray(new Role[0])
        );

    // 패스워드 암호화
    sellerSaveReqDto.getCommerceUser().encryptPassword(passwordEncoder);

    Seller seller = Seller.builder()
        .commerceUserDetail(sellerSaveReqDto.getCommerceUser().toEntity())
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

  private Optional<CommerceUserDetail> findCommerceUserByEmail(String email){
    return jpaCommerceUserDetailRepository.findOneByEmail(email);
  }
}

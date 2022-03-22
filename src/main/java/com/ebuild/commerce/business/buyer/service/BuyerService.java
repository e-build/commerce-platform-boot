package com.ebuild.commerce.business.buyer.service;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerResDto;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerSearchReqDto;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerSearchResDto;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.ebuild.commerce.exception.NotFoundException;
import com.ebuild.commerce.oauth.domain.ProviderType;
import com.google.common.collect.Lists;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyerService {

  private final BuyerQueryService buyerQueryService;
  private final JpaBuyerRepository jpaBuyerRepository;
  private final JpaRoleRepository jpaRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public BuyerResDto signup(BuyerSaveReqDto buyerSaveReqDto) {
    String email = buyerSaveReqDto.getAppUserDetails().getEmail();
    boolean alreadyExists = jpaBuyerRepository
        .findByEmail(email)
        .isPresent();

    if ( alreadyExists )
      throw new AlreadyExistsException(email, "이메일");

    // 권한 부여
    buyerSaveReqDto.getAppUserDetails()
        .setRoles(
            jpaRoleRepository
                .findAllByNameIn(Lists.newArrayList(RoleType.BUYER))
                .toArray(new Role[0])
        );

    // 패스워드 암호화
    buyerSaveReqDto.getAppUserDetails().encryptPassword(passwordEncoder);

    AppUserDetails appUserDetails = buyerSaveReqDto.getAppUserDetails().toEntity();
    appUserDetails.setEmailVerifiedYn("N");
    appUserDetails.setProviderType(ProviderType.EMAIL);
    Buyer buyer = Buyer.builder()
        .appUserDetails(appUserDetails)
        .receivingAddress(buyerSaveReqDto.getReceiveAddress().get())
        .build();

    Buyer savedBuyer = jpaBuyerRepository.save(buyer);

    return BuyerResDto.builder()
        .buyer(savedBuyer)
        .build();
  }

  @Transactional
  public BuyerResDto update(BuyerSaveReqDto buyerSaveReqDto) {
    Buyer buyer = buyerQueryService.findByEmail(buyerSaveReqDto.getAppUserDetails().getEmail());
    buyer.update(buyerSaveReqDto);

    return BuyerResDto.builder()
        .buyer(buyer)
        .build();
  }

  public void deleteOne(Long buyerId) {
    jpaBuyerRepository.delete(
        jpaBuyerRepository
            .findById(buyerId)
            .orElseThrow(()->new NotFoundException(String.valueOf(buyerId), "사용자"))
    );
  }

}

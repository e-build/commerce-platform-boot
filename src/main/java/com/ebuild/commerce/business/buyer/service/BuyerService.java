package com.ebuild.commerce.business.buyer.service;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.domain.dto.BuyerResDto;
import com.ebuild.commerce.business.buyer.domain.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.buyer.domain.dto.BuyerSearchReqDto;
import com.ebuild.commerce.business.buyer.domain.dto.BuyerSearchResDto;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.ebuild.commerce.exception.NotFoundException;
import com.google.common.collect.Lists;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyerService {

  private final JpaBuyerRepository jpaBuyerRepository;
  private final JpaAppUserDetailsRepository jpaAppUserDetailsRepository;
  private final JpaRoleRepository jpaRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public BuyerResDto signup(BuyerSaveReqDto buyerSaveReqDto) {
    findCommerceUserByEmail(buyerSaveReqDto.getAppUserDetails().getEmail())
        .ifPresent(user -> {
          throw new AlreadyExistsException("이미 다른 계정에서 사용중인 email 입니다.");
        });

    // 권한 부여
    buyerSaveReqDto.getAppUserDetails()
        .setRoles(
            jpaRoleRepository
                .findAllByNameIn(Lists.newArrayList(RoleType.BUYER))
                .toArray(new Role[0])
        );

    // 패스워드 암호화
    buyerSaveReqDto.getAppUserDetails().encryptPassword(passwordEncoder);

    Buyer buyer = Buyer.builder()
        .appUserDetails(buyerSaveReqDto.getAppUserDetails().toEntity())
        .receivingAddress(buyerSaveReqDto.getReceiveAddress().get())
        .build();

    jpaBuyerRepository.save(buyer);

    return BuyerResDto.builder()
        .buyer(buyer)
        .build();
  }

  @Transactional
  public BuyerResDto update(BuyerSaveReqDto buyerSaveReqDto) {
    AppUserDetails appUserDetails = findCommerceUserByEmail(
        buyerSaveReqDto.getAppUserDetails().getEmail())
        .orElseThrow(() -> new NotFoundException(
            "[" + buyerSaveReqDto.getAppUserDetails().getEmail() + "]에 해당하는 사용자는 존재하지 않습니다."));

    Buyer buyer = appUserDetails.getBuyer();
    buyer.update(buyerSaveReqDto);

    return BuyerResDto.builder()
        .buyer(buyer)
        .build();
  }

  @Transactional(readOnly = true)
  public BuyerResDto findOneById(Long buyerId) {
    return BuyerResDto.builder()
        .buyer(jpaBuyerRepository
            .findById(buyerId)
            .orElseThrow(()->new NotFoundException("해당 사용자를 찾을 수 없습니다."))
        ).build();
  }

  public void deleteOne(Long buyerId) {
    jpaBuyerRepository.delete(
        jpaBuyerRepository
            .findById(buyerId)
            .orElseThrow(()->new NotFoundException("해당 사용자를 찾을 수 없습니다."))
    );
  }

  public BuyerSearchResDto search(BuyerSearchReqDto buyerSearchReqDto) {
    return null;
  }

  private Optional<AppUserDetails> findCommerceUserByEmail(String email){
    return jpaAppUserDetailsRepository.findOneByEmail(email);
  }

}

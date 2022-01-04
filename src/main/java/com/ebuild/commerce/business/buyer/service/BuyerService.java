package com.ebuild.commerce.business.buyer.service;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.domain.dto.BuyerResDto;
import com.ebuild.commerce.business.buyer.domain.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.buyer.domain.dto.BuyerSearchReqDto;
import com.ebuild.commerce.business.buyer.domain.dto.BuyerSearchResDto;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import com.ebuild.commerce.business.user.role.CommerceRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import com.ebuild.commerce.business.user.role.repository.JpaRoleRepository;
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
  private final CommerceUserDetailRepository jpaCommerceUserDetailRepository;
  private final JpaRoleRepository jpaRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public BuyerResDto signup(BuyerSaveReqDto buyerSaveReqDto) {
    findCommerceUserByEmail(buyerSaveReqDto.getCommerceUser().getEmail())
        .ifPresent(user -> {
          throw new AlreadyExistsException("이미 다른 계정에서 사용중인 email 입니다.");
        });

    // 권한 부여
    buyerSaveReqDto.getCommerceUser()
        .setRoles(
            jpaRoleRepository
                .findAllByNameIn(Lists.newArrayList(CommerceRole.BUYER))
                .toArray(new Role[0])
        );

    // 패스워드 암호화
    buyerSaveReqDto.getCommerceUser().encryptPassword(passwordEncoder);

    Buyer buyer = Buyer.builder()
        .commerceUserDetail(buyerSaveReqDto.getCommerceUser().toEntity())
        .receivingAddress(buyerSaveReqDto.getReceiveAddress().get())
        .build();

    jpaBuyerRepository.save(buyer);

    return BuyerResDto.builder()
        .buyer(buyer)
        .build();
  }

  @Transactional
  public BuyerResDto update(BuyerSaveReqDto buyerSaveReqDto) {
    CommerceUserDetail commerceUserDetail = findCommerceUserByEmail(
        buyerSaveReqDto.getCommerceUser().getEmail())
        .orElseThrow(() -> new NotFoundException(
            "[" + buyerSaveReqDto.getCommerceUser().getEmail() + "]에 해당하는 사용자는 존재하지 않습니다."));

    Buyer buyer = commerceUserDetail.getBuyer();
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

  private Optional<CommerceUserDetail> findCommerceUserByEmail(String email){
    return jpaCommerceUserDetailRepository.findOneByEmail(email);
  }

}

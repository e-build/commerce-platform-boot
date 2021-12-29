package com.ebuild.commerce.business.user.buyer.service;

import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import com.ebuild.commerce.business.user.buyer.domain.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.user.buyer.domain.dto.BuyerSaveResDto;
import com.ebuild.commerce.business.user.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import com.ebuild.commerce.business.user.role.CommerceRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import com.ebuild.commerce.business.user.role.repository.JpaRoleRepository;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.ebuild.commerce.exception.CommerceServerError;
import com.google.common.collect.Lists;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyerService {

  private final JpaBuyerRepository jpaBuyerRepository;
  private final CommerceUserDetailRepository jpaCommerceUserDetailRepository;
  private final JpaRoleRepository jpaRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public BuyerSaveResDto signup(BuyerSaveReqDto buyerSaveReqDto) {
    String email = buyerSaveReqDto.getCommerceUser().getUsername();
    findCommerceUserByEmail(email)
        .ifPresent(user -> {
          throw new AlreadyExistsException("이미 다른 계정에서 사용중인 email 입니다.");
        });

    // 권한 부여
    buyerSaveReqDto.getCommerceUser()
        .setRoles(jpaRoleRepository
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

    return BuyerSaveResDto.of( buyer ) ;

//    findCommerceUserByEmail(email)
//        .orElseThrow(()-> new CommerceServerError("사용자 계정 생성 과정에서 서버 오류가 발생하였습니다."))
//        .getBuyer()

  }

  private Optional<CommerceUserDetail> findCommerceUserByEmail(String email){
    return jpaCommerceUserDetailRepository.findOneByEmail(email);
  }
}

package com.ebuild.commerce.business.buyer.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import com.ebuild.commerce.business.auth.controller.dto.AppUserDetailsSaveReqDto;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerResDto;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.dto.AddressReqDto;
import com.ebuild.commerce.oauth.domain.ProviderType;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@DisplayName("BuyerService 테스트")
class BuyerServiceTest {

  private BuyerService buyerService;
  private BuyerQueryService buyerQueryService;
  private PasswordEncoder passwordEncoder;
  @Mock
  private JpaBuyerRepository jpaBuyerRepository;
  @Mock
  private JpaRoleRepository jpaRoleRepository;

  private AppUserDetails appUserDetails;
  private Address receivingAddress;

  @BeforeEach
  void setUp(){
    passwordEncoder = new BCryptPasswordEncoder();
    buyerQueryService = new BuyerQueryService(jpaBuyerRepository);
    buyerService = new BuyerService(buyerQueryService, jpaBuyerRepository, jpaRoleRepository, passwordEncoder);

    appUserDetails = AppUserDetails.builder()
        .email("buyer@gmail.com")
        .password("password")
        .nickname("BUY_COMMERCE")
        .phoneNumber("010-9747-6477")
        .providerType(ProviderType.EMAIL)
        .build();

    receivingAddress = Address.builder()
        .baseAddress("baseAddress")
        .addressZipCode("addressZipCode")
        .detailAddress("detailAddress")
        .build();
  }

  @Test
  void signup() {
    // given
    BuyerSaveReqDto reqDto = new BuyerSaveReqDto();
    AppUserDetailsSaveReqDto appUserDetailsSaveReqDto = toAppUserDetailsSaveReqDto(appUserDetails);
    List<Role> roleArray = jpaRoleRepository.findAllByNameIn(Lists.newArrayList(RoleType.BUYER));
    appUserDetailsSaveReqDto.setRoles(roleArray.toArray(new Role[0]));

    reqDto.setAppUserDetails(appUserDetailsSaveReqDto);
    reqDto.setReceiveAddress(toAddressReqDto(receivingAddress));

    given(jpaBuyerRepository.findByEmail(any()))
        .willReturn(
            Optional.ofNullable(null)
        );

    given(jpaBuyerRepository.save(any()))
        .willReturn(
            Buyer.builder()
                .id(1L)
                .appUserDetails(reqDto.getAppUserDetails().toEntity())
                .receivingAddress(reqDto.getReceiveAddress().toEntity())
                .build()
        );

    // when
    BuyerResDto resDto = buyerService.signup(reqDto);

    // then
    assertThat(resDto.getBuyerId()).isEqualTo(1L);
    assertThat(resDto.getCommerceUser().getEmail()).isEqualTo(reqDto.getAppUserDetails().getEmail());
    assertThat(resDto.getCommerceUser().getNickname()).isEqualTo(reqDto.getAppUserDetails().getNickname());
    assertThat(resDto.getCommerceUser().getPhoneNumber()).isEqualTo(reqDto.getAppUserDetails().getPhoneNumber());
    assertThat(resDto.getReceivingAddress().getBaseAddress()).isEqualTo(reqDto.getReceiveAddress().getBaseAddress());
    assertThat(resDto.getReceivingAddress().getDetailAddress()).isEqualTo(reqDto.getReceiveAddress().getDetailAddress());
    assertThat(resDto.getReceivingAddress().getAddressZipCode()).isEqualTo(reqDto.getReceiveAddress().getAddressZipCode());
  }

  @Test
  void update() {
    BuyerSaveReqDto reqDto = new BuyerSaveReqDto();
    AppUserDetailsSaveReqDto appUserDetailsSaveReqDto = toAppUserDetailsSaveReqDto(appUserDetails);
    List<Role> roleArray = jpaRoleRepository.findAllByNameIn(Lists.newArrayList(RoleType.BUYER));
    appUserDetailsSaveReqDto.setRoles(roleArray.toArray(new Role[0]));

    reqDto.setAppUserDetails(appUserDetailsSaveReqDto);
    reqDto.setReceiveAddress(toAddressReqDto(receivingAddress));

    given(jpaBuyerRepository.findByEmail(any()))
        .willReturn(
            Optional.of(Buyer.builder()
                .id(1L)
                .appUserDetails(reqDto.getAppUserDetails().toEntity())
                .receivingAddress(reqDto.getReceiveAddress().toEntity())
                .build())
            );

    buyerService.update(reqDto);
  }

  private AddressReqDto toAddressReqDto(Address receivingAddress) {
    AddressReqDto addressReqDto = new AddressReqDto();
    addressReqDto.setBaseAddress(receivingAddress.getBaseAddress());
    addressReqDto.setAddressZipCode(receivingAddress.getAddressZipCode());
    addressReqDto.setDetailAddress(receivingAddress.getDetailAddress());
    return addressReqDto;
  }

  private AppUserDetailsSaveReqDto toAppUserDetailsSaveReqDto(AppUserDetails appUserDetails) {
    AppUserDetailsSaveReqDto appUserDetailsSaveReqDto = new AppUserDetailsSaveReqDto();
    appUserDetailsSaveReqDto.setEmail(appUserDetails.getEmail());
    appUserDetailsSaveReqDto.setPassword(appUserDetails.getPassword());
    appUserDetailsSaveReqDto.setNickname(appUserDetails.getNickname());
    appUserDetailsSaveReqDto.setPhoneNumber(appUserDetails.getPhoneNumber());
    return appUserDetailsSaveReqDto;
  }
}
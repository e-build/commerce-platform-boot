package com.ebuild.commerce.business.buyer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebuild.commerce.SampleDataInit;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.repository.JpaRoleRepository;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.exception.NotFoundException;
import com.ebuild.commerce.oauth.domain.ProviderType;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({
    SampleDataInit.class, SampleDataInit.Initializer.class
})
@DataJpaTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
class JpaBuyerRepositoryTest {

  @Autowired private JpaBuyerRepository jpaBuyerRepository;
  @Autowired private JpaRoleRepository jpaRoleRepository;

  @BeforeEach
  void setUp(){
    AppUserDetails appUserDetails = AppUserDetails
        .builder()
        .email("test@gmail.com")
        .password("password")
        .nickname("testuser")
        .phoneNumber("010-9747-6477")
        .providerType(ProviderType.EMAIL)
        .build();
    appUserDetails.setEmailVerifiedYn("Y");
    appUserDetails.addRoles(jpaRoleRepository
            .findAllByNameIn(Lists.newArrayList(RoleType.BUYER))
            .toArray(new Role[0]));

    Buyer buyer = Buyer.builder()
        .appUserDetails(appUserDetails)
        .receivingAddress(Address
            .builder()
            .baseAddress("baseAddress")
            .detailAddress("detailAddress")
            .addressZipCode("addressZipCode")
            .build())
        .build();
    jpaBuyerRepository.save(buyer);

  }

  @Test
  void findByEmail() {
    // given
    String email = "test@gmail.com";

    // when
    Buyer buyer = jpaBuyerRepository
        .findByEmail(email)
        .orElseThrow(() -> new NotFoundException(email, "이메일"));

    // then
    assertThat(buyer.getAppUserDetails().getEmail()).isEqualTo(email);
    assertThat(buyer.getAppUserDetails().getPassword()).isEqualTo("password");
    assertThat(buyer.getAppUserDetails().getNickname()).isEqualTo("testuser");
    assertThat(buyer.getAppUserDetails().getPhoneNumber()).isEqualTo("010-9747-6477");
    assertThat(buyer.getAppUserDetails().getProviderType()).isEqualTo(ProviderType.EMAIL);
    assertThat(buyer.getReceivingAddress().getBaseAddress()).isEqualTo("baseAddress");
    assertThat(buyer.getReceivingAddress().getDetailAddress()).isEqualTo("detailAddress");
    assertThat(buyer.getReceivingAddress().getAddressZipCode()).isEqualTo("addressZipCode");
  }

}
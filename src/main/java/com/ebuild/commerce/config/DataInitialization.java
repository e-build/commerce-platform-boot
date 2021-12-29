package com.ebuild.commerce.config;

import com.ebuild.commerce.business.cart.domain.entity.Cart;
import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.company.domain.entity.SettlementInfo;
import com.ebuild.commerce.business.user.admin.domain.Admin;
import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.role.CommerceRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import com.ebuild.commerce.business.user.seller.domain.Seller;
import com.ebuild.commerce.common.Address;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Configuration
public class DataInitialization {

  private final DataInitService dataInitService;

  @PostConstruct
  public void init(){
    dataInitService.init();
    dataInitService.init2();
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class DataInitService{

    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;

    public void init() {
//      em.clear();
//      em.flush();

      /*****************
       **** 회사 등록 ****
       ****************/
      Address address = createAddress();
      Company company = createCompany(address);
      em.persist(company);
    }

    public void init2(){

      /*****************
       **** 권한 등록 ****
       *****************/
      Role buyerRole = new Role(1L, CommerceRole.BUYER);
      Role sellerRole = new Role(2L, CommerceRole.SELLER);
      Role adminRole = new Role(3L, CommerceRole.ADMIN);

      /*****************
       **** 사용자 등록 ***
       *****************/
      CommerceUserDetail buyerDetail = createUserDetail("buyer@commerce.com", "1234qwer!@#$");
      buyerDetail.addRole(buyerRole);
      CommerceUserDetail sellerDetail = createUserDetail("seller@commerce.com", "1234qwer!@#$");
      sellerDetail.addRole(sellerRole);
      CommerceUserDetail adminDetail = createUserDetail("admin@commerce.com", "1234qwer!@#$");
      adminDetail.addRole(adminRole);

      Buyer buyer = createBuyer(buyerDetail);
      Seller seller = createSeller(sellerDetail, createCompany(createAddress()));
      Admin admin = createAdmin(adminDetail);

      em.persist(buyerRole);
      em.persist(sellerRole);
      em.persist(adminRole);
      em.persist(buyer);
      em.persist(seller);
      em.persist(admin);

    }

    private Buyer createBuyer(CommerceUserDetail userDetail) {
      return Buyer.builder()
          .commerceUserDetail(userDetail)
          .cart(Cart.newInstance())
          .build();
    }

    private Seller createSeller(CommerceUserDetail userDetail, Company company) {
      return Seller.builder()
          .commerceUserDetail(userDetail)
          .company(company)
          .shippingAddress(createAddress())
          .build();
    }

    private Admin createAdmin(CommerceUserDetail userDetail) {
      return Admin.builder()
          .commerceUserDetail(userDetail)
          .build();
    }

    private CommerceUserDetail createUserDetail(String username, String password) {
      return new CommerceUserDetail(username, passwordEncoder.encode(password));
    }

    private Company createCompany(Address address) {
      SettlementInfo settlementInfo = new SettlementInfo("69123123-12343123", "국민", 1);
      return new Company("seller company", "000-000000-000", "010-0000-0000", address, settlementInfo);
    }

    private Address createAddress() {
      return new Address("기본 주소지", "상세 주소", "67812");
    }
  }

}

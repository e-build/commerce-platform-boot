package com.ebuild.commerce.config;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.company.domain.entity.SettlementInfo;
import com.ebuild.commerce.business.product.domain.entity.ProductCategory;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.role.CommerceRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.common.Address;
import java.time.LocalDate;
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

      Product product1 = createProduct("와우 빡빡이 아저씨야", company,25000, 20000);
      Product product2 = createProduct("달러굿즈의 세계모험기", company, 10000, 8000);

      em.persist(product1);
      em.persist(product2);
    }

    public void init2(){

      /*****************
       **** 권한 등록 ****
       *****************/
      Role buyerRole = new Role(1L, CommerceRole.BUYER);
      Role sellerRole = new Role(2L, CommerceRole.SELLER);
      Role adminRole = new Role(3L, CommerceRole.ADMIN);

      em.persist(buyerRole);
      em.persist(sellerRole);
      em.persist(adminRole);


    }

    private Buyer createBuyer(CommerceUserDetail userDetail) {
      return Buyer.builder()
          .commerceUserDetail(userDetail)
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
      return CommerceUserDetail.builder()
          .email(username)
          .password(passwordEncoder.encode(password))
          .build();
    }

    private Company createCompany(Address address) {
      SettlementInfo settlementInfo = new SettlementInfo("69123123-12343123", "국민", 1);
      return new Company("seller company", "000-000000-000", "010-0000-0000", address, settlementInfo);
    }

    private Address createAddress() {
      return new Address("기본 주소지", "상세 주소", "67812");
    }

    private Product createProduct(String name, Company company, Integer normalAmount, Integer saleAmount){
      return new Product( name ,ProductStatus.SALE ,ProductCategory.BOOK
                      ,normalAmount ,saleAmount ,LocalDate.now()
                      ,LocalDate.now().plusDays(30) ,999 ,company);
    }
  }



}

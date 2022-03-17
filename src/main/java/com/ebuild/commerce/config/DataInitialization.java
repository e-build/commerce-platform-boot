package com.ebuild.commerce.config;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.company.domain.entity.SettlementInfo;
import com.ebuild.commerce.business.product.domain.entity.Category;
import com.ebuild.commerce.business.product.domain.entity.ProductCategory;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.oauth.domain.ProviderType;
import java.time.LocalDate;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Profile(value = "local")
@RequiredArgsConstructor
@Configuration
public class DataInitialization {

  private final DataInitService dataInitService;

  @PostConstruct
  public void init() {
    dataInitService.init();
    dataInitService.init2();
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class DataInitService {

    private final EntityManager em;
    private final PasswordEncoder passwordEncoder;

    public void init() {
//      em.clear();
//      em.flush();

      /*****************
       **** 회사 생성 ****
       ****************/
      Address address = createAddress();
      Company company = createCompany(address);
      em.persist(company);

      /********************
       **** 카테고리 생성 ****
       ********************/
      Category category1 = createCategory("국내서적", "001", "-1");
      Category category1_1 = createCategory("학문", "001001", "001");
      Category category1_2 = createCategory("소설", "001002", "001");
      Category category1_3 = createCategory("수필", "001003", "001");
      Category category2 = createCategory("해외서적", "002", "-1");
      Category category2_1 = createCategory("소설", "002001", "002");

      em.persist(category1);
      em.persist(category1_1);
      em.persist(category1_2);
      em.persist(category1_3);
      em.persist(category2);
      em.persist(category2_1);

      /********************
       **** 상품 생성 ****
       ********************/
      Product product1 = createProduct("매슬로우 욕구위계이론", company, category1_1, 25000, 21000);
      Product product2 = createProduct("달러굿즈의 세계모험기", company, category2_1, 10000, 8000);
      Product product3 = createProduct("홍길동은 왜 달리기가 빠를까?", company, category1_2,10000, 8000);

      em.persist(product1);
      em.persist(product2);
      em.persist(product3);
    }

    private Category createCategory(String name, String code, String superCode) {
      return Category.builder()
          .name(name)
          .code(code)
          .superCode(superCode)
          .build();
    }

    public void init2() {

      /*****************
       **** 권한 생성 ****
       *****************/
      Role buyerRole = new Role(1L, RoleType.BUYER);
      Role sellerRole = new Role(2L, RoleType.SELLER);
      Role adminRole = new Role(3L, RoleType.ADMIN);

      em.persist(buyerRole);
      em.persist(sellerRole);
      em.persist(adminRole);

      AppUserDetails appUserDetails = AppUserDetails.builder()
          .email("buyer@gmail.com")
          .password("$2a$10$zGIt8X9MFmR.TngaZQzvI..w3qIONVL3CDzTFDKvqP/8DiNpLbRdm") // 1234qwer!
          .nickname("이길동")
          .phoneNumber("010-9747-6477")
          .providerType(ProviderType.EMAIL)
          .build();
      appUserDetails.addRoles(buyerRole);
      appUserDetails.setEmailVerifiedYn("Y");

      Buyer buyer = Buyer.builder()
          .appUserDetails(appUserDetails)
          .build();

      em.persist(buyer);
    }

    private Buyer createBuyer(AppUserDetails userDetail) {
      return Buyer.builder()
          .appUserDetails(userDetail)
          .build();
    }

    private Seller createSeller(AppUserDetails userDetail, Company company) {
      return Seller.builder()
          .appUserDetails(userDetail)
          .company(company)
          .shippingAddress(createAddress())
          .build();
    }

    private Admin createAdmin(AppUserDetails userDetail) {
      return Admin.builder()
          .appUserDetails(userDetail)
          .build();
    }

    private AppUserDetails createUserDetail(String username, String password) {
      return AppUserDetails.builder()
          .email(username)
          .password(passwordEncoder.encode(password))
          .build();
    }

    private Company createCompany(Address address) {
      SettlementInfo settlementInfo = new SettlementInfo("69123123-12343123", "국민", 1);
      return new Company("seller company", "000-000000-000", "010-0000-0000", address,
          settlementInfo);
    }

    private Address createAddress() {
      return new Address("기본 주소지", "상세 주소", "67812");
    }

    private Product createProduct(String name, Company company,
        Category category, Integer normalAmount, Integer saleAmount) {
      Product product = Product.builder()
          .name(name)
          .productStatus(ProductStatus.SALE)
          .normalAmount(normalAmount)
          .saleAmount(saleAmount)
          .saleStartDate(LocalDate.now())
          .saleEndDate(LocalDate.now().plusDays(30))
          .quantity(999)
          .company(company)
          .build();

      product.addCategory(category);
      return product;
    }
  }


}

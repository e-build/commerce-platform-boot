package com.ebuild.commerce.config;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.company.domain.entity.SettlementInfo;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderStatus;
import com.ebuild.commerce.business.product.domain.entity.Category;
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
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    dataInitService.init3();
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  public static class DataInitService {

    private final EntityManager em;

    public void init() {
//      em.clear();
//      em.flush();

      /*****************
       **** ?????? ?????? ****
       ****************/
      Address address = createAddress();
      Company company = createCompany(address);
      em.persist(company);

      /********************
       **** ???????????? ?????? ****
       ********************/
      Category category1 = createCategory("????????????", "001", "-1");
      Category category1_1 = createCategory("??????", "001001", "001");
      Category category1_2 = createCategory("??????", "001002", "001");
      Category category1_3 = createCategory("??????", "001003", "001");
      Category category2 = createCategory("????????????", "002", "-1");
      Category category2_1 = createCategory("??????", "002001", "002");
      Category category2_2 = createCategory("??????", "002002", "002");

      em.persist(category1);
      em.persist(category1_1);
      em.persist(category1_2);
      em.persist(category1_3);
      em.persist(category2);
      em.persist(category2_1);
      em.persist(category2_2);
      em.flush();
      em.clear();

      /********************
       **** ?????? ?????? ****
       ********************/
      LocalDate criteria = LocalDate.of(2021, 1, 25);
//      Product product1 = createProduct("???????????? ??????????????????", company, category1_1, 2000L, 1800L, criteria);
//      Product product2 = createProduct("??????????????? ???????????????", company, category1_2, 4000L, 3600L, criteria.plusDays(30));
//      Product product3 = createProduct("???????????? ??? ???????????? ??????????", company, category1_3, 6000L, 5400L, criteria.plusDays(60));
//      Product product4 = createProduct("???????????? ???????????? ??????", company, category2_1, 10000L, 8200L, criteria.plusDays(90));
//      Product product5 = createProduct("??????????????? ???????????? ????????????", company, category2_1, 12000L, 11000L, criteria.plusDays(120));

      int eachProductVolume = 100;
      for (int i = 0; i < eachProductVolume; i++) {
        em.persist(createProduct("???????????? ??????????????????"+i, company, category1_1, 2000L, 1800L, criteria));
      }
      for (int i = 0; i < eachProductVolume; i++) {
        em.persist(createProduct("??????????????? ???????????????"+i, company, category1_2, 4000L, 3600L, criteria.plusDays(30)));
      }
      for (int i = 0; i < eachProductVolume; i++) {
        em.persist(createProduct("???????????? ??? ???????????? ??????????"+i, company, category1_3, 6000L, 5400L, criteria.plusDays(60)));
      }
      for (int i = 0; i < eachProductVolume; i++) {
        em.persist(createProduct("???????????? ???????????? ??????"+i, company, category2_1, 10000L, 8200L, criteria.plusDays(90)));
      }
      for (int i = 0; i < eachProductVolume; i++) {
        em.persist(createProduct("??????????????? ???????????? ????????????"+i, company, category2_2, 12000L, 11000L, criteria.plusDays(120)));
      }

      em.flush();
      em.clear();
    }

    public void init2() {
      /*****************
       **** ?????? ?????? ****
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
          .nickname("?????????")
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

    public void init3() {
      /*****************
       **** ?????? ?????? ****
       *****************/
      Buyer buyer = em.find(Buyer.class, 1L);
      Product product1 = (Product)em.createQuery("select p from Product p where p.name = :name")
          .setParameter("name","???????????? ??????????????????1")
          .getSingleResult();
      Product product2 = (Product)em.createQuery("select p from Product p where p.name = :name")
          .setParameter("name","??????????????? ???????????????1")
          .getSingleResult();
      Product product3 = (Product)em.createQuery("select p from Product p where p.name = :name")
          .setParameter("name","???????????? ??? ???????????? ??????????1")
          .getSingleResult();
      Product product4 = (Product)em.createQuery("select p from Product p where p.name = :name")
          .setParameter("name","???????????? ???????????? ??????1")
          .getSingleResult();
      Product product5 = (Product)em.createQuery("select p from Product p where p.name = :name")
          .setParameter("name","??????????????? ???????????? ????????????1")
          .getSingleResult();

      int eachOrderVolume = 100;
      LocalDateTime criteria = LocalDateTime.of(2021, 8, 15, 15,34, 1);
      for (int i = 0 ; i < eachOrderVolume ; i++){
        em.persist(createOrder(buyer, Lists.newArrayList(product1, product2), criteria, OrderStatus.PAYED));
      }
      for (int i = 0 ; i < eachOrderVolume ; i++){
        em.persist(createOrder(buyer, Lists.newArrayList(product2, product3), criteria.plusDays(3), OrderStatus.PAYED));
      }
      for (int i = 0 ; i < eachOrderVolume ; i++){
        em.persist(createOrder(buyer, Lists.newArrayList(product3, product4), criteria.plusDays(6), OrderStatus.PAYED));
      }
      for (int i = 0 ; i < eachOrderVolume ; i++){
        em.persist(createOrder(buyer, Lists.newArrayList(product4, product5), criteria.plusDays(9), OrderStatus.PAYED));
      }
      for (int i = 0 ; i < eachOrderVolume ; i++){
        em.persist(createOrder(buyer, Lists.newArrayList(product5, product1), criteria.plusDays(12), OrderStatus.PAYED));
      }

    }

    private Order createOrder(Buyer buyer, List<Product> productList, LocalDateTime orderDate, OrderStatus orderStatus) {
      Order order = Order.builder()
          .buyer(buyer)
          .orderDate(orderDate)
          .orderStatus(orderStatus)
          .build();

      order.setOrderProductList(productList
          .stream()
          .map(p -> OrderProduct.of(order, p, buyer.getReceivingAddress(), 1L))
          .collect(Collectors.toList()));
      return order;
    }

    private Company createCompany(Address address) {
      SettlementInfo settlementInfo = new SettlementInfo("69123123-12343123", "??????", 1);
      return new Company("seller company", "000-000000-000",
          "010-0000-0000", address, settlementInfo);
    }

    private Address createAddress() {
      return new Address("?????? ?????????", "?????? ??????", "67812");
    }

    private Product createProduct(String name, Company company,
        Category category, Long normalAmount, Long saleAmount, LocalDate saleStartDate) {
      return Product.builder()
          .name(name)
          .productStatus(ProductStatus.SALE)
          .category(category)
          .normalAmount(normalAmount)
          .saleAmount(saleAmount)
          .saleStartDate(saleStartDate)
          .saleEndDate(LocalDate.now().plusDays(30))
          .quantity(999L)
          .company(company)
          .build();
    }

    private Category createCategory(String name, String code, String superCode) {
      return Category.builder()
          .name(name)
          .code(code)
          .superCode(superCode)
          .build();
    }


  }

}

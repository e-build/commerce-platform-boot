package com.ebuild.commerce.business.order.service;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.business.order.controller.dto.DirectOrderReqDto;
import com.ebuild.commerce.business.order.controller.dto.OrderResDto;
import com.ebuild.commerce.business.order.controller.dto.OrderSearchCondition;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.repository.JpaOrderRepository;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.config.JsonHelper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("local")
@SpringBootTest
class OrderQueryServiceTest {

  @Autowired
  private JpaBuyerRepository jpaBuyerRepository;
  @Autowired
  private JpaProductRepository jpaProductRepository;
  @Autowired
  private OrderQueryService orderQueryService;
  @Autowired
  private JpaOrderRepository jpaOrderRepository;
  @Autowired
  private JsonHelper jsonHelper;

  private String buyerEmail = "buyer@gmail.com";

//  @BeforeEach
//  void setUp(){
//
//  }

  @Test
  void test() {
    // given
    Buyer buyer = jpaBuyerRepository.findByEmail(buyerEmail).get();
    List<Product> productList = jpaProductRepository.findAll();
    DirectOrderReqDto directOrderReqDto = new DirectOrderReqDto();
    jpaOrderRepository.save(Order.createDirectOrder(buyer, productList, directOrderReqDto));
    jpaOrderRepository.save(Order.createDirectOrder(buyer, productList, directOrderReqDto));
    jpaOrderRepository.save(Order.createDirectOrder(buyer, productList, directOrderReqDto));
    jpaOrderRepository.flush();

    LocalDateTime orderDate = LocalDateTime.now();;

    OrderSearchCondition condition = new OrderSearchCondition();
    condition.setOrderDateGoe(orderDate.minusMinutes(30));
    condition.setOrderDateLoe(orderDate.plusMinutes(30));
    condition.setBuyerEmail("buyer@commerce.com");

    // when
    Page<OrderResDto> pagingOrderList = orderQueryService.search(condition, PageRequest.of(30, 10));

    // then
    log.info("data : {}", jsonHelper.serialize(pagingOrderList));

  }


}
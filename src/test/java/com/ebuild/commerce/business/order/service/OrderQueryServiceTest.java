package com.ebuild.commerce.business.order.service;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.business.order.controller.dto.DirectOrderReqDto;
import com.ebuild.commerce.business.order.controller.dto.OrderPagingListDto;
import com.ebuild.commerce.business.order.controller.dto.OrderQueryParamsDto;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.repository.JpaOrderRepository;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.config.JsonHelper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    // when
    OrderPagingListDto orderPagingList = orderQueryService.search(buyerEmail, new OrderQueryParamsDto());

    // then
    log.info("data : {}", jsonHelper.serialize(orderPagingList));

  }


}
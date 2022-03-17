package com.ebuild.commerce.business.order.repository;

import com.ebuild.commerce.business.order.controller.dto.OrderQueryParamsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class OrderQueryRepositoryTest {

  @Autowired
  OrderQueryRepository repository;

  @Test
  void countBy(){
      // given

      // when
    Long count = repository.countBy("buyer@gmail.com", new OrderQueryParamsDto());

    // then

  }

}
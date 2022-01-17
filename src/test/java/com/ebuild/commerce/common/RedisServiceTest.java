package com.ebuild.commerce.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
class RedisServiceTest {

  @Autowired
  private RedisService redisService;

  @Test
  void basic_operation(){
    // given

    // when
    redisService.setData("one", "111");
    redisService.setData("two", "222");
    redisService.setData("three", "333");

    // then
    Assertions.assertThat(redisService.getData("one")).isEqualTo("111");
    Assertions.assertThat(redisService.getData("two")).isEqualTo("222");
    Assertions.assertThat(redisService.getData("three")).isEqualTo("333");
    Assertions.assertThat(redisService.getData("four")).isNull();
  }

}
package com.ebuild.commerce.business.order.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import com.ebuild.commerce.business.order.controller.dto.OrderResDto;
import com.ebuild.commerce.business.order.domain.entity.Payment;
import com.ebuild.commerce.business.order.domain.entity.PaymentMeans;
import com.ebuild.commerce.business.order.service.OrderQueryService;
import com.ebuild.commerce.business.order.service.OrderService;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderStatus;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.JwtConfig;
import com.ebuild.commerce.oauth.service.SecurityUserDetailsService;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@Slf4j
@Import({
    JwtConfig.class,
    SecurityUserDetailsService.class,
    JpaAppUserDetailsRepository.class,
    SecurityUserDetailsService.class
})
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = OrderApiController.class)
@DisplayName("OrderApiController 테스트")
class OrderApiControllerTest {

  private MockMvc mvc;
  @MockBean
  private OrderService orderService;
  @MockBean
  private OrderQueryService orderQueryService;
  @MockBean
  private JsonHelper jsonHelper;

  private final String ORDER_V1_API_PATH = "/api/v1/orders";

  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders
        .standaloneSetup(new OrderApiController(orderService, orderQueryService, jsonHelper))
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  @Test
  @DisplayName("바로 주문 테스트")
  void createDirectOrder() throws Exception {
    // given
    LocalDateTime now = LocalDateTime.now();
    given(orderService.createOrder(any(), any()))
            .willReturn(
                OrderResDto.builder()
                    .id(1L)
                    .orderStatus(OrderStatus.PAYED)
//                    .orderProductList()
                    .orderDate(now)
                    .payment(Payment.builder()
                        .paymentAmounts(1000L)
                        .paymentMeans(PaymentMeans.CASH)
                        .paymentDateTime(now)
                        .cardVendor("신한카드")
                        .build())
                .build()
            );

    // when
    ResultActions action = mvc.perform(
            post(ORDER_V1_API_PATH + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getCreateOrderBody())
        )
        .andExpect(status().isOk())
        .andDo(print());

    // then
    action
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("result.data.order.id").value(1L))
        .andExpect(jsonPath("result.data.order.status").value("PAYED"))
        .andExpect(jsonPath("result.data.order.payment.paymentMeans").value("CASH"))
        .andExpect(jsonPath("result.data.order.payment.paymentMeans").value("신한카드"))
        .andExpect(jsonPath("result.data.order.payment.paymentMeans").value("1000"))
    ;
  }

  private String getCreateOrderBody() {
    return "{\n"
        + "    \"orderLineList\" : [\n"
        + "        {\n"
        + "            \"productId\" : 1,\n"
        + "            \"quantity\" : 1\n"
        + "        },\n"
        + "        {\n"
        + "            \"productId\" : 2,\n"
        + "            \"quantity\" : 1\n"
        + "        }\n"
        + "    ],\n"
        + "    \"baseOrderInfo\" : {\n"
        + "        \"useBuyerBasicAddress\" : false,\n"
        + "        \"receivingAddress\" : {\n"
        + "            \"baseAddress\" : \"수령 배송지 기본 - 주문시 입력 \",\n"
        + "            \"detailAddress\" : \"수령 배송지 상세 - 주문시 입력 \",\n"
        + "            \"addressZipCode\" : \"67814 - 주문시 입력 \"\n"
        + "        },\n"
        + "        \"payment\" : {\n"
        + "            \"paymentMeans\": \"CREDIT_CARD\",\n"
        + "            \"cardVendor\": \"신한카드\",\n"
        + "            \"paymentAmounts\": \"48000\",\n"
        + "            \"paymentDateTime\": \"2021-12-31T02:00:33\"\n"
        + "        }\n"
        + "    }\n"
        + "}";
  }

  @Test
  void findOrders() {

  }

}
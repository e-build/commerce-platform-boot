package com.ebuild.commerce.business.buyer.controller;

import com.ebuild.commerce.business.auth.controller.dto.AppUserDetailsSaveReqDto;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerResDto;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.service.BuyerQueryService;
import com.ebuild.commerce.business.buyer.service.BuyerService;
import com.ebuild.commerce.business.order.controller.dto.OrderResDto;
import com.ebuild.commerce.business.order.controller.dto.OrderSearchCondition;
import com.ebuild.commerce.business.order.domain.entity.Payment;
import com.ebuild.commerce.business.order.domain.entity.PaymentMeans;
import com.ebuild.commerce.business.order.service.OrderQueryService;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderStatus;
import com.ebuild.commerce.common.Address;
import com.ebuild.commerce.common.dto.AddressReqDto;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.SecurityConfig;
import com.ebuild.commerce.oauth.domain.ProviderType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Import({
    JsonHelper.class
})
@DisplayName("BuyerApiController 테스트")
@ExtendWith(SpringExtension.class)
@WebMvcTest(
    controllers = BuyerApiController.class,
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = SecurityConfig.class
        )
    }
)
class BuyerApiControllerTest {

  private MockMvc mvc;
  @MockBean
  private OrderQueryService orderQueryService;
  @MockBean
  private BuyerQueryService buyerQueryService;
  @MockBean
  private BuyerService buyerService;
  @Autowired
  private JsonHelper jsonHelper;

  private final String BUYER_V1_API_PATH = "/api/v1/buyers";
  private AppUserDetails appUserDetails;
  private Address receivingAddress;

  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders
        .standaloneSetup(new BuyerApiController(orderQueryService, buyerQueryService, buyerService, jsonHelper))
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();

    appUserDetails = AppUserDetails.builder()
        .email("buyer@gmail.com")
        .password("password")
        .nickname("BUY_COMMERCE")
        .phoneNumber("010-9747-6477")
        .providerType(ProviderType.EMAIL)
        .build();

    receivingAddress = Address.builder()
        .baseAddress("baseAddress")
        .addressZipCode("addressZipCode")
        .detailAddress("detailAddress")
        .build();
  }

  @Test
  @DisplayName("구매자 회원가입")
  void buyerSignup() throws Exception {
    // given
    BuyerSaveReqDto buyerSaveReqDto = new BuyerSaveReqDto();
    buyerSaveReqDto.setAppUserDetails(toAppUserDetailsSaveReqDto(appUserDetails));
    buyerSaveReqDto.setReceiveAddress(toAddressReqDto(receivingAddress));

    given(buyerService.signup(any()))
        .willReturn(
            BuyerResDto.builder()
                .buyer(Buyer.builder()
                    .id(1L)
                    .appUserDetails(appUserDetails)
                    .receivingAddress(receivingAddress)
                    .build())
                .build());

    // when
    ResultActions action = mvc.perform(
            MockMvcRequestBuilders
                .post(BUYER_V1_API_PATH + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonHelper.serialize(buyerSaveReqDto))
        )
        .andExpect(status().isCreated())
        .andDo(print());

    // then
    action
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("data.buyer.buyerId").value(1))
        .andExpect(jsonPath("result").value(true))
//        .andExpect(jsonPath("error").value(null))
        .andExpect(jsonPath("data.buyer.commerceUser.email").value(appUserDetails.getEmail()))
        .andExpect(jsonPath("data.buyer.commerceUser.nickname").value(appUserDetails.getNickname()))
        .andExpect(jsonPath("data.buyer.commerceUser.phoneNumber").value(appUserDetails.getPhoneNumber()))
        .andExpect(jsonPath("data.buyer.receivingAddress.baseAddress").value(receivingAddress.getBaseAddress()))
        .andExpect(jsonPath("data.buyer.receivingAddress.detailAddress").value(receivingAddress.getDetailAddress()))
        .andExpect(jsonPath("data.buyer.receivingAddress.addressZipCode").value(receivingAddress.getAddressZipCode()))
    ;
  }

  @Test
  @DisplayName("구매자 회원정보 수정")
  void update() throws Exception {
    // given
    BuyerSaveReqDto buyerSaveReqDto = new BuyerSaveReqDto();
    buyerSaveReqDto.setAppUserDetails(toAppUserDetailsSaveReqDto(appUserDetails));
    String nicknameToUpdate = "update nickname";
    buyerSaveReqDto.getAppUserDetails().setNickname(nicknameToUpdate);
    buyerSaveReqDto.setReceiveAddress(toAddressReqDto(receivingAddress));

    long buyerId = 1L;
    BuyerResDto buyerResDto = BuyerResDto.builder()
        .buyer(Buyer.builder()
            .id(buyerId)
            .appUserDetails(appUserDetails)
            .receivingAddress(receivingAddress)
            .build())
        .build();
    buyerResDto.getCommerceUser().setNickname(nicknameToUpdate);

    given(buyerService.update(any()))
        .willReturn(buyerResDto);

    // when
    ResultActions action = mvc.perform(
            MockMvcRequestBuilders
                .put(BUYER_V1_API_PATH + "/" + buyerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonHelper.serialize(buyerSaveReqDto))
        )
        .andDo(print());

    // then
    action
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("data.buyer.buyerId").value(buyerResDto.getBuyerId()))
        .andExpect(jsonPath("result").value(true))
        .andExpect(jsonPath("data.buyer.commerceUser.email").value(buyerResDto.getCommerceUser().getEmail()))
        .andExpect(jsonPath("data.buyer.commerceUser.nickname").value(nicknameToUpdate))
        .andExpect(jsonPath("data.buyer.commerceUser.phoneNumber").value(buyerResDto.getCommerceUser().getPhoneNumber()))
        .andExpect(jsonPath("data.buyer.receivingAddress.baseAddress").value(buyerResDto.getReceivingAddress().getBaseAddress()))
        .andExpect(jsonPath("data.buyer.receivingAddress.detailAddress").value(buyerResDto.getReceivingAddress().getDetailAddress()))
        .andExpect(jsonPath("data.buyer.receivingAddress.addressZipCode").value(buyerResDto.getReceivingAddress().getAddressZipCode()))
    ;
  }

  @Test
  @DisplayName("회원정보 조회")
  void findOne() throws Exception {
    // given
    BuyerResDto buyerResDto = BuyerResDto.builder()
        .buyer(Buyer.builder()
            .appUserDetails(appUserDetails)
            .receivingAddress(receivingAddress)
            .build())
        .build();
    long buyerId = 1L;
    given(buyerQueryService.findOneById(any()))
        .willReturn(buyerResDto);

    // when
    ResultActions action = mvc.perform(
            MockMvcRequestBuilders
                .get(BUYER_V1_API_PATH + "/" + buyerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print());

    // then
    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("data.buyer.buyerId").value(buyerResDto.getBuyerId()))
        .andExpect(jsonPath("result").value(true))
        .andExpect(jsonPath("data.buyer.commerceUser.email").value(buyerResDto.getCommerceUser().getEmail()))
        .andExpect(jsonPath("data.buyer.commerceUser.nickname").value(buyerResDto.getCommerceUser().getNickname()))
        .andExpect(jsonPath("data.buyer.commerceUser.phoneNumber").value(buyerResDto.getCommerceUser().getPhoneNumber()))
        .andExpect(jsonPath("data.buyer.receivingAddress.baseAddress").value(buyerResDto.getReceivingAddress().getBaseAddress()))
        .andExpect(jsonPath("data.buyer.receivingAddress.detailAddress").value(buyerResDto.getReceivingAddress().getDetailAddress()))
        .andExpect(jsonPath("data.buyer.receivingAddress.addressZipCode").value(buyerResDto.getReceivingAddress().getAddressZipCode()))
      ;
  }

  @Test
  @DisplayName("회원탈퇴")
  void delete() throws Exception {
    // given
    long buyerId = 1L;

    // when
    ResultActions action = mvc.perform(
            MockMvcRequestBuilders
                .delete(BUYER_V1_API_PATH + "/" + buyerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        .andDo(print());

    // then
    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("data").value(Matchers.anEmptyMap()))
        .andExpect(jsonPath("result").value(true))
        .andExpect(jsonPath("error").value(Matchers.nullValue()))
        ;
  }

  @Test
  @DisplayName("내 주문 조회")
  void findMyOrders() throws Exception {
    // given
    OrderSearchCondition orderSearchCondition = new OrderSearchCondition();
    orderSearchCondition.setOrderStatusList(Lists.newArrayList(OrderStatus.PAYED.name()));
    orderSearchCondition.setBuyerId(1L);
    orderSearchCondition.setBuyerEmail("buyer@gmail.com");

    PageRequest pageRequest = PageRequest.of(0, 10);

    List<OrderResDto> orderList = Lists.newArrayList();
    for ( int i = 1 ; i <= 10 ; i++ ){
      LocalDateTime now = LocalDateTime.now();
      orderList.add(
          OrderResDto.builder()
              .id(Long.parseLong(String.valueOf(i)))
              .totalPayAmount(10000L)
              .totalSaleAmount(10000L)
              .orderDate(now)
              .payment(Payment.builder()
                  .cardVendor("신한카드")
                  .paymentDateTime(now)
                  .paymentMeans(PaymentMeans.CREDIT_CARD)
                  .paymentAmounts(10000L)
                  .build())
              .orderStatus(OrderStatus.PAYED)
              .build());
    }
    long buyerId = 1;
    given(orderQueryService.search(any(), any()))
        .willReturn(new PageImpl<>(orderList, pageRequest, 100L));

    // when
    ResultActions action = mvc.perform(
            MockMvcRequestBuilders
                .get(BUYER_V1_API_PATH + "/" + buyerId + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("buyerId", String.valueOf(orderSearchCondition.getBuyerId()))
                .param("buyerEmail", orderSearchCondition.getBuyerEmail())
                .param("page", String.valueOf(pageRequest.getPageNumber()))
                .param("size", String.valueOf(pageRequest.getPageSize()))
        )
        .andDo(print());

    // then
    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("error").value(Matchers.nullValue()))
        .andExpect(jsonPath("result").value(true))
        .andExpect(jsonPath("data.order.content[0].id").value(1))
        .andExpect(jsonPath("data.order.content[0].orderStatus").value(OrderStatus.PAYED.name()))
        .andExpect(jsonPath("data.order.content[0].totalSaleAmount").value(10000L))
        .andExpect(jsonPath("data.order.content[0].totalPayAmount").value(10000L))
        .andExpect(jsonPath("data.order.content[0].payment.paymentMeans").value("CREDIT_CARD"))
        .andExpect(jsonPath("data.order.content[0].payment.cardVendor").value("신한카드"))
        .andExpect(jsonPath("data.order.content[0].payment.paymentAmounts").value(10000L))
    ;
  }

  private AddressReqDto toAddressReqDto(Address receivingAddress) {
    AddressReqDto addressReqDto = new AddressReqDto();
    addressReqDto.setBaseAddress(receivingAddress.getBaseAddress());
    addressReqDto.setAddressZipCode(receivingAddress.getAddressZipCode());
    addressReqDto.setDetailAddress(receivingAddress.getDetailAddress());
    return addressReqDto;
  }

  private AppUserDetailsSaveReqDto toAppUserDetailsSaveReqDto(AppUserDetails appUserDetails) {
    AppUserDetailsSaveReqDto appUserDetailsSaveReqDto = new AppUserDetailsSaveReqDto();
    appUserDetailsSaveReqDto.setEmail(appUserDetails.getEmail());
    appUserDetailsSaveReqDto.setPassword(appUserDetails.getPassword());
    appUserDetailsSaveReqDto.setNickname(appUserDetails.getNickname());
    appUserDetailsSaveReqDto.setPhoneNumber(appUserDetails.getPhoneNumber());
    return appUserDetailsSaveReqDto;
  }
}
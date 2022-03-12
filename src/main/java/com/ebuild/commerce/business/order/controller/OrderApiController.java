package com.ebuild.commerce.business.order.controller;

import com.ebuild.commerce.business.auth.domain.UserSubject;
import com.ebuild.commerce.config.security.annotation.CurrentUser;
import com.ebuild.commerce.business.order.domain.dto.DirectOrderReqDto;
import com.ebuild.commerce.business.order.service.OrderService;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderApiController {

  private final OrderService orderService;
  private final JsonHelper jsonHelper;

  // 바로주문
  @PostMapping("")
  public ResponseEntity<CommonResponse> createDirectOrder(
      @CurrentUser UserSubject userSubject,
      @RequestBody @Valid DirectOrderReqDto directOrderReqDto) {

    return ResponseEntity.ok(
        CommonResponse.OK(
            Pair.of("order",
                orderService.createOrder(userSubject.getEmail(), directOrderReqDto))
        )
    );
  }

  // 주문 상세 조회

  // 주문 리스트 조회 (페이징)


}

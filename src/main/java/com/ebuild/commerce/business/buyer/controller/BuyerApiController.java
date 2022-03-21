package com.ebuild.commerce.business.buyer.controller;

import com.ebuild.commerce.business.auth.domain.UserSubject;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerSearchReqDto;
import com.ebuild.commerce.business.buyer.service.BuyerQueryService;
import com.ebuild.commerce.business.buyer.service.BuyerService;
import com.ebuild.commerce.business.order.controller.dto.OrderSearchCondition;
import com.ebuild.commerce.business.order.service.OrderQueryService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.annotation.CurrentUser;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/buyers")
@RestController
public class BuyerApiController {

  private final OrderQueryService orderQueryService;
  private final BuyerQueryService buyerQueryService;
  private final BuyerService buyerService;
  private final JsonHelper jsonHelper;

  @PostMapping("")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid BuyerSaveReqDto buyerSaveReqDto) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(CommonResponse.OK("buyer", buyerService.signup(buyerSaveReqDto)));
  }

  @PutMapping("/{buyerId}")
  public ResponseEntity<CommonResponse> update(
      @PathVariable("buyerId") Long buyerId
      , @RequestBody @Valid BuyerSaveReqDto buyerSaveReqDto) {
    return ResponseEntity.ok(
        CommonResponse.OK("buyer", buyerService.update(buyerSaveReqDto))
    );
  }

  @GetMapping("/{buyerId}")
  public ResponseEntity<CommonResponse> findOne(
      @PathVariable("buyerId") Long buyerId) {
    return ResponseEntity.ok(
        CommonResponse.OK("buyer", buyerQueryService.findOneById(buyerId))
    );
  }

  @DeleteMapping("/{buyerId}")
  public ResponseEntity<CommonResponse> delete(
      @PathVariable("buyerId") Long buyerId) {
    buyerService.deleteOne(buyerId);
    return ResponseEntity.ok(
        CommonResponse.OK()
    );
  }

  // 주문 리스트 조회 (페이징)
  @GetMapping("/{buyerId}/orders")
  public ResponseEntity<CommonResponse> findMyOrders(
      @PathVariable Long buyerId,
      @CurrentUser UserSubject userSubject,
      OrderSearchCondition condition,
      @PageableDefault(sort={"id"}, direction = Direction.DESC) Pageable pageable) {
    condition.setBuyerId(buyerId);
    condition.setBuyerEmail(userSubject.getEmail());
    return ResponseEntity.ok(
        CommonResponse.OK("order", orderQueryService.search(condition, pageable))
    );
  }


}

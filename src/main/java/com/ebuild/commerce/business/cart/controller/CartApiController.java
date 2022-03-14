package com.ebuild.commerce.business.cart.controller;

import com.ebuild.commerce.business.cart.domain.dto.CartLineListPlusMinusReqDto;
import com.ebuild.commerce.business.cart.service.CartService;
import com.ebuild.commerce.business.order.domain.dto.BaseOrderCreateReqDto;
import com.ebuild.commerce.common.http.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
@RestController
public class CartApiController {

  private final CartService cartService;

  @PatchMapping("/{cartId}/add-products")
  public ResponseEntity<CommonResponse> addCartLineList(
      @PathVariable("cartId") Long cartId,
      @RequestBody @Valid CartLineListPlusMinusReqDto cartLineListPlusMinusReqDto) {
    cartService.addCartLineList(cartId, cartLineListPlusMinusReqDto);
    return ResponseEntity.ok(
        CommonResponse.OK()
    );
  }

  @PatchMapping("/{cartId}/minus-products")
  public ResponseEntity<CommonResponse> minusCartLineList(
      @PathVariable("cartId") Long cartId,
      @RequestBody @Valid CartLineListPlusMinusReqDto cartLineListPlusMinusReqDto) {
    cartService.removeCartLineList(cartId, cartLineListPlusMinusReqDto);
    return ResponseEntity.ok(
        CommonResponse.OK()
    );
  }

  @GetMapping("/{cartId}")
  public ResponseEntity<CommonResponse> findOne(
      @PathVariable("cartId") Long cartId) {
    return ResponseEntity.ok(
        CommonResponse.OK(
            "cart", cartService.findById(cartId)
        )
    );
  }

  @PostMapping("/{cartId}/order")
  public ResponseEntity<CommonResponse> createCartOrder(
      @PathVariable("cartId") Long cartId
      , @RequestBody @Valid BaseOrderCreateReqDto cartBaseOrderCreateReqDto) {
    return ResponseEntity.ok(
        CommonResponse.OK(
            "order", cartService.createOrder(cartId, cartBaseOrderCreateReqDto)
        )
    );
  }

}

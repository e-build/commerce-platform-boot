package com.ebuild.commerce.controller;

import com.ebuild.commerce.business.user.buyer.domain.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.user.buyer.service.BuyerService;
import com.ebuild.commerce.common.http.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/buyers")
@RestController
public class BuyerApiController {

  private final BuyerService buyerService;

  @PostMapping("")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid BuyerSaveReqDto buyerSaveReqDto){
    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("buyer", buyerService.signup(buyerSaveReqDto)))
    );
  }

  @GetMapping("/{buyerId}")
  public ResponseEntity<CommonResponse> findOne(
      @PathVariable("buyerId") Long buyerId){
    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("buyer", buyerService.findOneById(buyerId)))
    );
  }



}

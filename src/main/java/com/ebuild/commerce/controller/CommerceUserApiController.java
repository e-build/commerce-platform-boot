package com.ebuild.commerce.controller;

import com.ebuild.commerce.business.user.buyer.domain.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.user.buyer.service.BuyerService;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserLoginReqDto;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveReqDto;
import com.ebuild.commerce.business.user.commerceUserDetail.service.CommerceUserService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.jwt.TokenDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.jdbc.metadata.CommonsDbcp2DataSourcePoolMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class CommerceUserApiController {

  private final BuyerService buyerService;
  private final CommerceUserService commerceUserService;
  private final JsonHelper jsonHelper;
  
  @PostMapping("")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid BuyerSaveReqDto buyerSaveReqDto){

    log.info("buyerSaveReqDto: {}", buyerSaveReqDto);

    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("buyer", buyerService.signup(buyerSaveReqDto)))
    );
  }

  @PostMapping("/authenticate")
  public ResponseEntity<CommonResponse> authenticate(
      @RequestBody @Valid CommerceUserLoginReqDto commerceUserLoginReqDto ){
    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("token", commerceUserService.login(commerceUserLoginReqDto)))
    );
  }


}

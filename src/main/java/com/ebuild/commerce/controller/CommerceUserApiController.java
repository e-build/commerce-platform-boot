package com.ebuild.commerce.controller;

import com.ebuild.commerce.business.user.admin.domain.dto.AdminSaveReqDto;
import com.ebuild.commerce.business.user.admin.service.AdminService;
import com.ebuild.commerce.business.user.buyer.domain.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.user.buyer.service.BuyerService;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserLoginReqDto;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.dto.CommerceUserSaveReqDto;
import com.ebuild.commerce.business.user.commerceUserDetail.service.CommerceUserService;
import com.ebuild.commerce.business.user.seller.domain.dto.SellerSaveReqDto;
import com.ebuild.commerce.business.user.seller.service.SellerService;
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

  private final JsonHelper jsonHelper;
  private final CommerceUserService commerceUserService;
  private final BuyerService buyerService;
  private final SellerService sellerService;
  private final AdminService adminService;

  @PostMapping("/buyer")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid BuyerSaveReqDto buyerSaveReqDto){
    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("buyer", buyerService.signup(buyerSaveReqDto)))
    );
  }

  @PostMapping("/seller")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid SellerSaveReqDto sellerSaveReqDto){
    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("seller", sellerService.signup(sellerSaveReqDto)))
    );
  }

  @PostMapping("/admin")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid AdminSaveReqDto adminSaveReqDto){
    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("admin", adminService.signup(adminSaveReqDto)))
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

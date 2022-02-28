package com.ebuild.commerce.business.auth.controller;

import com.ebuild.commerce.business.admin.domain.dto.AdminSaveReqDto;
import com.ebuild.commerce.business.admin.service.AdminService;
import com.ebuild.commerce.business.auth.service.AppUserDetailsService;
import com.ebuild.commerce.business.seller.domain.dto.SellerSaveReqDto;
import com.ebuild.commerce.business.seller.service.SellerService;
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
@RequestMapping("/api/v1/users")
@RestController
public class AppUserApiController {

  private final JsonHelper jsonHelper;
  private final AppUserDetailsService appUserDetailsService;

  private final SellerService sellerService;
  private final AdminService adminService;

  @PostMapping("/seller")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid SellerSaveReqDto sellerSaveReqDto) {
    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("seller", sellerService.signup(sellerSaveReqDto)))
    );
  }

  @PostMapping("/admin")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid AdminSaveReqDto adminSaveReqDto) {
    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("admin", adminService.signup(adminSaveReqDto)))
    );
  }

}

package com.ebuild.commerce.business.auth.controller;

import com.ebuild.commerce.business.auth.domain.dto.LoginReqDto;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.service.CommerceAuthService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.annotation.CurrentUser;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthApiController {

  private final CommerceAuthService commerceAuthServcie;
  private final JsonHelper jsonHelper;

  @PostMapping("/login")
  public ResponseEntity<CommonResponse> login(@RequestBody @Valid LoginReqDto loginReqDto){
    return ResponseEntity.ok(CommonResponse.OK(Pair.of("token", commerceAuthServcie.login(loginReqDto))));
  }

  @PostMapping("/logout")
  public ResponseEntity<CommonResponse> logout(
      @CurrentUser AppUserDetails appUserDetails) {
    commerceAuthServcie.logout(appUserDetails);
    return ResponseEntity.ok(
        CommonResponse.OK()
    );
  }

  @GetMapping
  public ResponseEntity<CommonResponse> test(@CurrentUser AppUserDetails appUserDetails){
    log.info("asdfsadfa sdfasdf : {}", jsonHelper.serialize(appUserDetails));
    return ResponseEntity.ok(CommonResponse.OK());
  }



}

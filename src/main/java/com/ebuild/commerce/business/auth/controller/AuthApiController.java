package com.ebuild.commerce.business.auth.controller;

import com.ebuild.commerce.business.auth.domain.UserSubject;
import com.ebuild.commerce.business.auth.domain.dto.LoginReqDto;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.service.CommerceAuthService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.annotation.CurrentUser;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final CommerceAuthService commerceAuthService;
  private final JsonHelper jsonHelper;

  @PostMapping("/login")
  public ResponseEntity<CommonResponse> login(@RequestBody @Valid LoginReqDto loginReqDto){
    return ResponseEntity.ok(CommonResponse.OK("login", commerceAuthService.login(loginReqDto)));
  }

  @PostMapping("/logout")
  public ResponseEntity<CommonResponse> logout(
      @CurrentUser AppUserDetails appUserDetails) {
    commerceAuthService.logout(appUserDetails);
    return ResponseEntity.ok(CommonResponse.OK());
  }

  @GetMapping("/refresh")
  public ResponseEntity<CommonResponse> tokenRefresh(){
    return ResponseEntity.ok(CommonResponse.OK());
  }


  @GetMapping("/test")
  public ResponseEntity<CommonResponse> test(@CurrentUser UserSubject userSubject){
    log.info("Security Global Authentication annotation operation check - userSubject : {}", jsonHelper.serialize(userSubject));
    return ResponseEntity.ok(CommonResponse.OK());
  }



}

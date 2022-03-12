package com.ebuild.commerce.business.auth.controller;

import com.ebuild.commerce.business.auth.service.CommerceAuthService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.oauth.token.JWTProvider;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/oauth")
@RestController
public class OauthApiController {

  private final JWTProvider JWTProvider;
  private final CommerceAuthService commerceAuthService;

  @GetMapping("/redirect")
  public ResponseEntity<CommonResponse> redirect(
      @RequestParam("token") String token,
      HttpServletRequest request ){
    return ResponseEntity.ok(CommonResponse.OK("login", commerceAuthService.oauthLogin(token)));
  }

}

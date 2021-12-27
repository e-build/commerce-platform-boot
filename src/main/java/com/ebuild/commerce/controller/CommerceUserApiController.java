package com.ebuild.commerce.controller;

import com.ebuild.commerce.business.user.commerceUser.CommerceUserLoginReqDto;
import com.ebuild.commerce.business.user.commerceUser.CommerceUserService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.jwt.TokenDto;
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
@RequestMapping("/api/v1/user")
@RestController
public class CommerceUserApiController {

  private final CommerceUserService commerceUserService;
  private final JsonHelper jsonHelper;

  @PostMapping("/login")
  public ResponseEntity<CommonResponse> login(
      @RequestBody @Valid CommerceUserLoginReqDto commerceUserLoginReqDto ){

    log.info("commerceUserLoginReqDto : {}", jsonHelper.serialize(commerceUserLoginReqDto));
    TokenDto token;
    try {
      token = commerceUserService.login(commerceUserLoginReqDto);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

    return ResponseEntity.ok(
        CommonResponse.OK(Pair.of("token", token))
    );
  }


}

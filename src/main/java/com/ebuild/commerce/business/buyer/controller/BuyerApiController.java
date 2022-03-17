package com.ebuild.commerce.business.buyer.controller;

import com.ebuild.commerce.business.buyer.controller.dto.BuyerSaveReqDto;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerSearchReqDto;
import com.ebuild.commerce.business.buyer.service.BuyerQueryService;
import com.ebuild.commerce.business.buyer.service.BuyerService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final BuyerQueryService buyerQueryService;
  private final BuyerService buyerService;
  private final JsonHelper jsonHelper;

  @PostMapping("")
  public ResponseEntity<CommonResponse> buyerSignup(
      @RequestBody @Valid BuyerSaveReqDto buyerSaveReqDto) {
    return ResponseEntity.ok(
        CommonResponse.OK("buyer", buyerService.signup(buyerSaveReqDto))
    );
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
      @PathVariable("buyerId") Long buyerId
      , Principal principal) {
    log.info("principal : {}", jsonHelper.serialize(principal));

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

  @GetMapping("")
  public ResponseEntity<CommonResponse> search(
      @RequestBody @Valid BuyerSearchReqDto buyerSearchReqDto) {
    return ResponseEntity.ok(
        CommonResponse.OK("buyer", buyerService.search(buyerSearchReqDto))
    );
  }


}

package com.ebuild.commerce.controller;

import com.ebuild.commerce.business.product.domain.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.domain.dto.ProductSaveResDto;
import com.ebuild.commerce.business.product.service.ProductCommandService;
import com.ebuild.commerce.common.http.CommonResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/companys/{companyId}")
@RestController
@RequiredArgsConstructor
public class SellerApiController {

  private final ProductCommandService productCommandService;

  @PostMapping("/products")
  public ResponseEntity<CommonResponse> register(
      @PathVariable("companyId") Long companyId,
      @RequestBody @Valid ProductSaveReqDto productSaveReqDto){

    return ResponseEntity.ok(
        CommonResponse.OK(
            Pair.of(
                "product", ProductSaveResDto.of(productCommandService.register(companyId, productSaveReqDto))
            )
        )
    );
  }

  @PutMapping("/products")
  public ResponseEntity<CommonResponse> update(
      @PathVariable("companyId") Long companyId,
      @RequestBody @Valid ProductSaveReqDto productSaveReqDto){

    return ResponseEntity.ok(
        CommonResponse.OK(
            Pair.of(
                "product", ProductSaveResDto.of(productCommandService.update(companyId, productSaveReqDto))
            )
        )
    );
  }

  @DeleteMapping("/products/{productId}")
  public ResponseEntity<CommonResponse> delete(
      @PathVariable("companyId") Long companyId,
      @PathVariable("productId") Long productId ){

    productCommandService.delete(productId);
    return ResponseEntity.ok(CommonResponse.OK());
  }

//  @GetMapping("/products")
//  public ResponseEntity<CommonResponse> searchProduct(
//      @PathVariable("companyId") Long companyId,
//      @RequestParam @Valid ProductQueryReqDto productQueryReqDto){
//
//    productCommandService.queryByCondition(companyId, productQueryReqDto);
//
//    return ResponseEntity.ok(
//        CommonResponse.OK(
//        )
//    );
//  }

}

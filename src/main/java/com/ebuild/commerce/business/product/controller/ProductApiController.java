package com.ebuild.commerce.business.product.controller;

import com.ebuild.commerce.business.product.domain.dto.ProductChangeStatusReqDto;
import com.ebuild.commerce.business.product.domain.dto.ProductSearchReqDto;
import com.ebuild.commerce.business.product.domain.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.domain.dto.ProductResDto;
import com.ebuild.commerce.business.product.service.ProductCommandService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/api/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductApiController {

  private final ProductCommandService productCommandService;
  private final JsonHelper jsonHelper;

  @PostMapping("")
  public ResponseEntity<CommonResponse> register(
      @RequestBody @Valid ProductSaveReqDto productSaveReqDto){

    return ResponseEntity.ok(
        CommonResponse.OK(
            "product"
            , ProductResDto.builder()
                .product(productCommandService.register(productSaveReqDto))
                .build()
        )
    );
  }

  @PutMapping("/{productId}")
  public ResponseEntity<CommonResponse> update(
      @PathVariable Long productId,
      @RequestBody @Valid ProductSaveReqDto productSaveReqDto){

    return ResponseEntity.ok(
        CommonResponse.OK(
            "product"
            , ProductResDto.builder()
                .product(productCommandService.update(productId, productSaveReqDto))
                .build()
        )
    );
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<CommonResponse> delete(
      @PathVariable("productId") Long productId ){

    productCommandService.delete(productId);
    return ResponseEntity.ok(
        CommonResponse.OK()
    );
  }

  @GetMapping("")
  public ResponseEntity<CommonResponse> searchProduct(
      @PageableDefault(sort={"id"}, direction= Direction.DESC)
      @Valid ProductSearchReqDto productSearchReqDto){

    log.info("productSearchReqDto : {}", jsonHelper.serialize(productSearchReqDto));

    return ResponseEntity.ok(
        CommonResponse.OK(
            "products",
            productCommandService.searchByCondition(productSearchReqDto)
        )
    );
  }
  @PutMapping("/{productId}/change-status")
  public ResponseEntity<CommonResponse> changeStatus(
      @PathVariable("productId") Long productId,
      @RequestBody @Valid ProductChangeStatusReqDto productChangeStatusReqDto){

    productCommandService.changeStatus(productId, productChangeStatusReqDto);

    return ResponseEntity.ok(
        CommonResponse.OK()
    );
  }

}

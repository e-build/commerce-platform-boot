package com.ebuild.commerce.business.product.controller;

import com.ebuild.commerce.business.product.controller.dto.PageableProductSearchCondition;
import com.ebuild.commerce.business.product.controller.dto.ProductChangeStatusReqDto;
import com.ebuild.commerce.business.product.controller.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.service.ProductQueryService;
import com.ebuild.commerce.business.product.service.ProductService;
import com.ebuild.commerce.common.http.CommonResponse;
import com.ebuild.commerce.config.JsonHelper;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final ProductService productService;
  private final ProductQueryService productQueryService;
  private final JsonHelper jsonHelper;

  @PostMapping("")
  public ResponseEntity<CommonResponse> register(
      @RequestBody @Valid ProductSaveReqDto productSaveReqDto) {

    return ResponseEntity.ok(
        CommonResponse.OK("product", productService.register(productSaveReqDto))
    );
  }

  @PutMapping("/{productId}")
  public ResponseEntity<CommonResponse> update(
      @PathVariable Long productId,
      @RequestBody @Valid ProductSaveReqDto productSaveReqDto) {

    return ResponseEntity.ok(
        CommonResponse.OK("product", productService.update(productId, productSaveReqDto))
    );
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<CommonResponse> delete(
      @PathVariable("productId") Long productId) {

    productService.delete(productId);
    return ResponseEntity.ok(
        CommonResponse.OK()
    );
  }

  @GetMapping("")
  public ResponseEntity<CommonResponse> searchProduct(
      @PageableDefault(sort = {"id"}, direction = Direction.DESC) @Valid PageableProductSearchCondition condition) {
    log.info("productSearchReqDto : {}", jsonHelper.serialize(condition));

    return ResponseEntity.ok(
        CommonResponse.OK("result", productQueryService.search(condition))
    );
  }

  @PutMapping("/{productId}/change-status")
  public ResponseEntity<CommonResponse> changeStatus(
      @PathVariable("productId") Long productId,
      @RequestBody @Valid ProductChangeStatusReqDto productChangeStatusReqDto) {

    productService.changeStatus(productId, productChangeStatusReqDto);

    return ResponseEntity.ok(
        CommonResponse.OK()
    );
  }

}

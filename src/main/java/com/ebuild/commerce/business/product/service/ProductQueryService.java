package com.ebuild.commerce.business.product.service;

import com.ebuild.commerce.business.product.controller.dto.PageableProductSearchCondition;
import com.ebuild.commerce.business.product.controller.dto.ProductResDto;
import com.ebuild.commerce.business.product.repository.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductQueryService {

  private ProductQueryRepository productQueryRepository;

  public Page<ProductResDto> search(PageableProductSearchCondition condition) {
    return productQueryRepository.search(condition);
  }
}

package com.ebuild.commerce.business.product.service;

import com.ebuild.commerce.business.product.controller.dto.ProductSearchCondition;
import com.ebuild.commerce.business.product.controller.dto.ProductResDto;
import com.ebuild.commerce.business.product.repository.ProductQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductQueryService {

  private final ProductQueryRepository productQueryRepository;

  @Transactional(readOnly = true)
  public Page<ProductResDto> search(ProductSearchCondition condition, Pageable pageable) {
    return productQueryRepository.search(condition, pageable);
  }

}

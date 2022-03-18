package com.ebuild.commerce.business.product.service;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.business.product.controller.dto.ProductChangeStatusReqDto;
import com.ebuild.commerce.business.product.controller.dto.ProductResDto;
import com.ebuild.commerce.business.product.controller.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.domain.entity.Category;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.ebuild.commerce.business.product.repository.JpaCategoryRepository;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final JpaCategoryRepository jpaCategoryRepository;
  private final JpaProductRepository jpaProductRepository;
  private final JpaCompanyRepository jpaCompanyRepository;

  @Transactional
  public ProductResDto register(ProductSaveReqDto productSaveReqDto) {
    return ProductResDto.of(
        jpaProductRepository.save(
            Product.create(
                jpaProductRepository
                , findCompany(productSaveReqDto.getProduct().getCompanyId())
                , findCategory(productSaveReqDto.getProduct().getCategoryId())
                , productSaveReqDto
            )
        ));
  }

  @Transactional
  public ProductResDto update(Long productId, ProductSaveReqDto productSaveReqDto) {
    Product product = findProduct(productSaveReqDto.getProduct().getId());
    product.update(
        jpaProductRepository
        , findCompany(productSaveReqDto.getProduct().getCompanyId())
        , findCategory(productSaveReqDto.getProduct().getCategoryId())
        , productSaveReqDto
    );

    return ProductResDto.of(product);
  }

  public void delete(Long productId) {
    jpaProductRepository.delete(findProduct(productId));
  }

  @Transactional
  public void changeStatus(Long productId, ProductChangeStatusReqDto productChangeStatusReqDto) {
    Product product = findProduct(productId);
    ProductStatus targetStatus = ProductStatus.fromValue(
        productChangeStatusReqDto.getProductStatus());

    if (product.getProductStatus() == targetStatus) {
      throw new IllegalArgumentException(
          "상품 [" + product.getName() + "]은 이미 [" + productChangeStatusReqDto.getProductStatus()
              + "] 상태 입니다."
      );
    }

    product.changeSaleStatus(targetStatus);
  }

  private Category findCategory(Long categoryId) {
    return jpaCategoryRepository.findById(categoryId)
        .orElseThrow(()-> new NotFoundException(String.valueOf(categoryId), "카테고리"));
  }

  private Product findProduct(Long productId) {
    return jpaProductRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException(String.valueOf(productId), "상품"));
  }

  private Company findCompany(Long companyId) {
    return jpaCompanyRepository
        .findById(companyId)
        .orElseThrow(() -> new NotFoundException(String.valueOf(companyId), "회사"));
  }
}

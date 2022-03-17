package com.ebuild.commerce.business.product.service;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.business.product.controller.dto.ProductResDto;
import com.ebuild.commerce.business.product.domain.entity.Category;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.ebuild.commerce.business.product.controller.dto.ProductChangeStatusReqDto;
import com.ebuild.commerce.business.product.controller.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.controller.dto.ProductSearchReqDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaCategoryRepository;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.exception.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandService {

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
                , findCategoryList(productSaveReqDto.getProduct().getCategoryIdList())
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
        , findCategoryList(productSaveReqDto.getProduct().getCategoryIdList())
        , productSaveReqDto
    );

    return ProductResDto.of(product);
  }

  public void delete(Long productId) {
    jpaProductRepository.delete(findProduct(productId));
  }

  public Page<Product> searchWithPaging(ProductSearchReqDto productSearchReqDto) {
    return jpaProductRepository.findAll(
        productSearchReqDto.getPageable()
    );
  }

  public Page<Product> searchByCondition(ProductSearchReqDto productSearchReqDto) {
    return jpaProductRepository.findAllByCompanyAndNameContaining(
        findCompany(productSearchReqDto.getCompanyId())
        , productSearchReqDto.getName()
        , productSearchReqDto.getPageable()
    );
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

  private List<Category> findCategoryList(List<Long> categoryIdList) {
    return jpaCategoryRepository.findByIdIn(categoryIdList);
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

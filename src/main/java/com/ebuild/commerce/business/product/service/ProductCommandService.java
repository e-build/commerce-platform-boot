package com.ebuild.commerce.business.product.service;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.ebuild.commerce.business.product.domain.dto.ProductChangeStatusReqDto;
import com.ebuild.commerce.business.product.domain.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.domain.dto.ProductSearchReqDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandService {

  private final JpaProductRepository jpaProductRepository;
  private final JpaCompanyRepository jpaCompanyRepository;

  private Product findProduct(Long productId) {
    return jpaProductRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 상품 ID 입니다. : " + productId));
  }

  private Company findCompany(Long companyId){
    return jpaCompanyRepository
        .findById(companyId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 회사 ID 입니다. : " + companyId));
  }

  public Product register(ProductSaveReqDto productSaveReqDto) {
    return jpaProductRepository.save(
        Product.create(
            jpaProductRepository
            , findCompany(productSaveReqDto.getProduct().getCompanyId())
            , productSaveReqDto
        )
    );
  }

  @Transactional
  public Product update(Long productId, ProductSaveReqDto productSaveReqDto) {
    Product findProduct = findProduct(productSaveReqDto.getProduct().getId());
    findProduct.update(
        jpaProductRepository
        , findCompany(productSaveReqDto.getProduct().getCompanyId())
        , productSaveReqDto
    );

    return findProduct;
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
    ProductStatus targetStatus = ProductStatus.fromValue(productChangeStatusReqDto.getProductStatus());

    if (product.getProductStatus() == targetStatus)
      throw new IllegalArgumentException(
          "상품 [" + product.getName() + "]은 이미 [" + productChangeStatusReqDto.getProductStatus() + "] 상태 입니다."
      );

    product.changeSaleStatus(targetStatus);
  }
}

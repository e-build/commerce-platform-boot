package com.ebuild.commerce.business.product.service;

import com.ebuild.commerce.business.company.domain.Company;
import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.business.product.domain.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCommandService {

  private final JpaProductRepository jpaProductRepository;
  private final JpaCompanyRepository jpaCompanyRepository;

  public Product register(Long companyId, ProductSaveReqDto productSaveReqDto) {
    return jpaProductRepository.save(
        Product.create(jpaProductRepository, findCompany(companyId), productSaveReqDto)
    );
  }

  @Transactional
  public Product update(Long companyId, ProductSaveReqDto productSaveReqDto) {
    Product findProduct = findProduct(productSaveReqDto.getProduct().getId());
    findProduct.update(jpaProductRepository, findCompany(companyId), productSaveReqDto);
    return findProduct;
  }

  public void delete(Long productId) {
    jpaProductRepository.delete(findProduct(productId));
  }

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



}

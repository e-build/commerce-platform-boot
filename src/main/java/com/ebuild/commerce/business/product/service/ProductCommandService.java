package com.ebuild.commerce.business.product.service;

import com.ebuild.commerce.business.company.domain.Company;
import com.ebuild.commerce.business.company.repository.JpaCompanyRepository;
import com.ebuild.commerce.business.product.domain.dto.ProductSaveReqDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.exception.AlreadyExistsException;
import com.ebuild.commerce.exception.NotFoundException;
import com.ebuild.commerce.util.JsonUtils;
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

  @Transactional
  public Product register(Long companyId, ProductSaveReqDto productSaveReqDto) {

    Company company = jpaCompanyRepository
        .findById(companyId)
        .orElseThrow(() -> new NotFoundException("존재하지 않는 회사 ID 입니다. : " + companyId));

    jpaProductRepository
        .findByCompanyAndName(company, productSaveReqDto.getProduct().getName())
        .ifPresent(p -> {
          throw new AlreadyExistsException("["+p.getName()+"] 은 이미 존재하는 상품명입니다.");
        });

    Product product = productSaveReqDto.toEntity();
    product.registerCompany(company);

    return jpaProductRepository.save(product);
  }
}

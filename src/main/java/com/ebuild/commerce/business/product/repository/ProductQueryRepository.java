package com.ebuild.commerce.business.product.repository;

import static com.ebuild.commerce.business.company.domain.entity.QCompany.company;
import static com.ebuild.commerce.business.product.domain.entity.QProduct.product;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.ebuild.commerce.business.product.controller.dto.PageableProductSearchCondition;
import com.ebuild.commerce.business.product.controller.dto.ProductResDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public Page<ProductResDto> search(PageableProductSearchCondition condition) {

    List<Product> productList = jpaQueryFactory.select(product)
        .from(product)
        .join(product.company, company)
        .where(
            nameEq(condition.getParams().getName())
            , companyNameEq(condition.getParams().getCompanyName())
            , normalAmountBetween(
                condition.getParams().getNormalAmountGoe()
                , condition.getParams().getNormalAmountLoe()
            )
            , saleAmountBetween(
                condition.getParams().getSaleAmountGoe()
                , condition.getParams().getSaleAmountLoe()
            )
            , quantityBetween(
                condition.getParams().getQuantityGoe()
                , condition.getParams().getQuantityLoe()
            )
            , productStatusIn(condition.getParams().getProductStatusList())
            , categoryIdIn(condition.getParams().getCategoryIdList())
        )
        .offset(condition.getPageable().getOffset())
        .limit(condition.getPageable().getPageSize())
        .fetch();

    long totalCount = jpaQueryFactory.select(product)
        .from(product)
        .join(product.company, company)
        .where(
            nameEq(condition.getParams().getName())
            , companyNameEq(condition.getParams().getCompanyName())
            , normalAmountBetween(condition.getParams().getNormalAmountGoe(),
                condition.getParams().getNormalAmountLoe())
            , saleAmountBetween(condition.getParams().getSaleAmountGoe(),
                condition.getParams().getSaleAmountLoe())
            , quantityBetween(condition.getParams().getQuantityGoe(),
                condition.getParams().getQuantityLoe())
            , productStatusIn(condition.getParams().getProductStatusList())
            , categoryIdIn(condition.getParams().getCategoryIdList())
        )
        .offset(condition.getPageable().getOffset())
        .limit(condition.getPageable().getPageSize())
        .fetchCount();

    List<ProductResDto> collect = productList.stream().map(ProductResDto::of)
        .collect(Collectors.toList());

    return new PageImpl<>(collect, condition.getPageable(), totalCount);
  }

  private BooleanExpression normalAmountBetween(int normalAmountGoe, int normalAmountLoe) {
    return normalAmountGoe(normalAmountGoe).and(normalAmountLoe(normalAmountLoe));
  }

  private BooleanExpression saleAmountBetween(int saleAmountGoe, int saleAmountLoe) {
    return saleAmountGoe(saleAmountGoe).and(saleAmountLoe(saleAmountLoe));
  }

  private BooleanExpression quantityBetween(int quantityGoe, int quantityLoe) {
    return quantityGoe(quantityGoe).and(quantityLoe(quantityLoe));
  }

  private BooleanExpression quantityLoe(Integer quantityLoe) {
    return quantityLoe != null ? product.quantity.loe(quantityLoe) : null;
  }


  private BooleanExpression quantityGoe(Integer quantityGoe) {
    return quantityGoe != null ? product.quantity.goe(quantityGoe) : null;
  }

  private BooleanExpression categoryIdIn(List<Long> categoryIdList) {
    return !CollectionUtils.isEmpty(categoryIdList) ? product.category.id.in(categoryIdList) : null;
  }

  private BooleanExpression saleAmountLoe(Integer saleAmountLoe) {
    return saleAmountLoe != null ? product.saleAmount.loe(saleAmountLoe) : null;
  }

  private BooleanExpression saleAmountGoe(Integer saleAmountGoe) {
    return saleAmountGoe != null ? product.saleAmount.goe(saleAmountGoe) : null;
  }

  private BooleanExpression normalAmountLoe(Integer normalAmountLoe) {
    return normalAmountLoe != null ? product.normalAmount.loe(normalAmountLoe) : null;
  }

  private BooleanExpression normalAmountGoe(Integer normalAmountGoe) {
    return normalAmountGoe != null ? product.normalAmount.goe(normalAmountGoe) : null;
  }

  private BooleanExpression productStatusIn(List<String> productStatusList) {
    List<ProductStatus> collect = productStatusList.stream()
        .map(ProductStatus::fromValue)
        .collect(Collectors.toList());
    return !CollectionUtils.isEmpty(productStatusList) ? product.productStatus.in(collect) : null;
  }

  private BooleanExpression nameEq(String name) {
    return isNotBlank(name) ? product.name.equalsIgnoreCase(name) : null;
  }

  private BooleanExpression companyNameEq(String companyName) {
    return isNotBlank(companyName) ? company.name.equalsIgnoreCase(companyName) : null;
  }

}

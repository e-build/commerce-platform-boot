package com.ebuild.commerce.business.product.repository;

import static com.ebuild.commerce.business.company.domain.entity.QCompany.company;
import static com.ebuild.commerce.business.product.domain.entity.QProduct.product;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.ebuild.commerce.business.product.controller.dto.ProductSearchCondition;
import com.ebuild.commerce.business.product.controller.dto.ProductResDto;
import com.ebuild.commerce.business.product.controller.dto.QProductResDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.domain.entity.ProductStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductQueryRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public Page<ProductResDto> search(ProductSearchCondition condition, Pageable pageable) {

    JPAQuery<ProductResDto> contentsQuery = jpaQueryFactory
        .select(new QProductResDto(product.id, product.name, product.productStatus,
            product.normalAmount, product.saleAmount, product.category, product.saleStartDate,
            product.saleEndDate, product.quantity, product.company, product.createdAt,
            product.updatedAt))
        .from(product)
        .join(product.company, company)
        .where(
            nameEq(condition.getName())
            , companyNameEq(condition.getCompanyName())
            , normalAmountGoe(condition.getNormalAmountGoe())
            , normalAmountLoe(condition.getNormalAmountLoe())
            , saleAmountGoe(condition.getSaleAmountGoe())
            , saleAmountLoe(condition.getSaleAmountLoe())
            , quantityGoe(condition.getQuantityGoe())
            , quantityLoe(condition.getQuantityLoe())
            , productStatusIn(condition.getProductStatusList())
            , categoryIdIn(condition.getCategoryIdList())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<ProductResDto> contents = contentsOrderBy(contentsQuery, pageable.getSort());

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(product.count())
        .from(product)
        .join(product.company, company)
        .where(
            nameEq(condition.getName())
            , companyNameEq(condition.getCompanyName())
            , normalAmountGoe(condition.getNormalAmountGoe())
            , normalAmountLoe(condition.getNormalAmountLoe())
            , saleAmountGoe(condition.getSaleAmountGoe())
            , saleAmountLoe(condition.getSaleAmountLoe())
            , quantityGoe(condition.getQuantityGoe())
            , quantityLoe(condition.getQuantityLoe())
            , productStatusIn(condition.getProductStatusList())
            , categoryIdIn(condition.getCategoryIdList())
        );

    return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
  }

  private List<ProductResDto> contentsOrderBy(JPAQuery<ProductResDto> contenstQuery, Sort sort) {
    for (Sort.Order o : sort) {
      PathBuilder<Product> orderByExpression = new PathBuilder<>(Product.class, "product");
      contenstQuery.orderBy(
          new OrderSpecifier(
              o.isAscending() ? Order.ASC : Order.DESC
              , orderByExpression.get(o.getProperty())
          )
      );
    }
    return contenstQuery.fetch();
  }

  private BooleanExpression quantityLoe(Integer quantityLoe) {
    return quantityLoe != null ? product.quantity.loe(quantityLoe) : null;
  }

  private BooleanExpression quantityGoe(Integer quantityGoe) {
    return quantityGoe != null ? product.quantity.goe(quantityGoe) : null;
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

  private BooleanExpression nameEq(String name) {
    return isNotBlank(name) ? product.name.equalsIgnoreCase(name) : null;
  }

  private BooleanExpression companyNameEq(String companyName) {
    return isNotBlank(companyName) ? company.name.equalsIgnoreCase(companyName) : null;
  }

  private BooleanExpression categoryIdIn(List<Long> categoryIdList) {
    if (CollectionUtils.isEmpty(categoryIdList)) {
      return null;
    }
    return product.category.id.in(categoryIdList);
  }

  private BooleanExpression productStatusIn(List<String> productStatusList) {
    if (CollectionUtils.isEmpty(productStatusList)) {
      return null;
    }

    List<ProductStatus> collect = productStatusList.stream()
        .map(ProductStatus::fromValue)
        .collect(Collectors.toList());
    return product.productStatus.in(collect);
  }

}

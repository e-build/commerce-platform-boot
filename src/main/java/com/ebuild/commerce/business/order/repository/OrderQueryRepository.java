package com.ebuild.commerce.business.order.repository;

import static com.ebuild.commerce.business.auth.domain.entity.QAppUserDetails.appUserDetails;
import static com.ebuild.commerce.business.buyer.domain.QBuyer.buyer;
import static com.ebuild.commerce.business.delivery.domain.entity.QDelivery.*;
import static com.ebuild.commerce.business.order.domain.entity.QOrder.order;
import static com.ebuild.commerce.business.orderProduct.domain.entity.QOrderProduct.orderProduct;
import static com.ebuild.commerce.business.product.domain.entity.QProduct.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.ebuild.commerce.business.order.controller.dto.OrderResDto;
import com.ebuild.commerce.business.order.controller.dto.OrderSearchCondition;
import com.ebuild.commerce.business.order.controller.dto.QOrderResDto;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.domain.entity.PaymentMeans;
import com.ebuild.commerce.business.orderProduct.controller.dto.OrderProductResDto;
import com.ebuild.commerce.business.orderProduct.controller.dto.QOrderProductResDto;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderStatus;
import com.ebuild.commerce.business.orderProduct.domain.entity.QOrderProduct;
import com.ebuild.commerce.common.Querydsl4RepositorySupport;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class OrderQueryRepository extends Querydsl4RepositorySupport {

  public OrderQueryRepository() {
    super(Order.class);
  }

  public Page<OrderResDto> search(OrderSearchCondition condition, Pageable pageable) {
    // Order 조회
    JPAQuery<OrderResDto> contentsQuery = getContentsQuery(condition, pageable);
    List<OrderResDto> contents = contentsQuery.fetch();
    List<Long> orderIds = contents.stream().map(OrderResDto::getId).collect(Collectors.toList());

    // OrderProduct 조회
    Map<Long, List<OrderProductResDto>> orderProductResDtoListMap =
        getOrderProductList(condition, orderIds)
        .stream()
        .collect(Collectors.groupingBy(OrderProductResDto::getOrderId));

    // OrderResDto, OrderProductResDto 조합
    List<OrderResDto> result = Lists.newArrayList();
    for (Long orderId : orderProductResDtoListMap.keySet()) {
      for (OrderResDto o : contents) {
        if (Objects.equals(o.getId(), orderId)) {
          o.setOrderProductList(orderProductResDtoListMap.get(o.getId()));
          result.add(o);
          break;
        }
      }
    }

    return PageableExecutionUtils.getPage(result, pageable, () -> getCountQuery(condition).fetchOne());
  }

  private JPAQuery<Long> getCountQuery(OrderSearchCondition condition) {
    return select(order.count())
        .from(order)
        .join(order.buyer, buyer)
        .join(buyer.appUserDetails, appUserDetails)
        .join(order.orderProductList, orderProduct)
        .join(orderProduct.product, product)
        .where(
            orderStatusIn(condition.getOrderStatusList()),
            buyerIdEq(condition.getBuyerId()),
            buyerEmailEq(condition.getBuyerEmail()),
            paymentMeansIn(condition.getPaymentMeansList()),
            paymentAmountsGoe(condition.getPaymentAmountsGoe()),
            paymentAmountsLoe(condition.getPaymentAmountsLoe()),
            orderDateGoe(condition.getOrderDateGoe()),
            orderDateLoe(condition.getOrderDateLoe()),
            paymentDateTimeGoe(condition.getPaymentDateTimeGoe()),
            paymentDateTimeLoe(condition.getPaymentDateTimeLoe()),
            productNameContains(condition.getProductName())
        );
  }

  private JPAQuery<OrderResDto> getContentsQuery(
      OrderSearchCondition condition, Pageable pageable) {

    QOrderProduct op = new QOrderProduct("op");
    return select(
        new QOrderResDto(
            order.id,
            order.orderStatus,
            select(op.saleAmount.sum())
                .from(op)
                .where(op.order.eq(order)),
            order.payment.paymentAmounts,
            order.orderDate,
            order.payment)
        )
        .from(order)
        .join(order.buyer, buyer)
        .join(buyer.appUserDetails, appUserDetails)
        .where(
            orderStatusIn(condition.getOrderStatusList()),
            buyerIdEq(condition.getBuyerId()),
            buyerEmailEq(condition.getBuyerEmail()),
            paymentMeansIn(condition.getPaymentMeansList()),
            paymentAmountsGoe(condition.getPaymentAmountsGoe()),
            paymentAmountsLoe(condition.getPaymentAmountsLoe()),
            orderDateGoe(condition.getOrderDateGoe()),
            orderDateLoe(condition.getOrderDateLoe()),
            paymentDateTimeGoe(condition.getPaymentDateTimeGoe()),
            paymentDateTimeLoe(condition.getPaymentDateTimeLoe())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());
  }

  private List<OrderProductResDto> getOrderProductList(
      OrderSearchCondition condition, List<Long> orderIds) {
    return select(
            new QOrderProductResDto(
                orderProduct.id,
                orderProduct.order.id,
                product.id,
                product.name,
                product.category,
                orderProduct.normalAmount,
                orderProduct.saleAmount,
                orderProduct.quantity,
                orderProduct.delivery
            )
        )
        .from(orderProduct)
        .join(orderProduct.product, product)
        .join(orderProduct.delivery, delivery)
        .where(
            orderIdIn(orderIds),
            productNameContains(condition.getProductName())
        )
        .fetch();
  }

  private Predicate orderIdIn(List<Long> orderIds) {
    return !CollectionUtils.isEmpty(orderIds) ? orderProduct.order.id.in(orderIds) : null;
  }

  private BooleanExpression paymentDateTimeLoe(LocalDateTime paymentDateTimeLoe) {
    return !Objects.isNull(paymentDateTimeLoe) ? order.payment.paymentDateTime.loe(
        paymentDateTimeLoe) : null;
  }

  private BooleanExpression paymentDateTimeGoe(LocalDateTime paymentDateTimeGoe) {
    return !Objects.isNull(paymentDateTimeGoe) ? order.payment.paymentDateTime.goe(
        paymentDateTimeGoe) : null;
  }

  private BooleanExpression orderDateLoe(LocalDateTime orderDateLoe) {
    return !Objects.isNull(orderDateLoe) ? order.orderDate.loe(orderDateLoe) : null;
  }

  private BooleanExpression orderDateGoe(LocalDateTime orderDateGoe) {
    return !Objects.isNull(orderDateGoe) ? order.orderDate.goe(orderDateGoe) : null;
  }

  private BooleanExpression paymentAmountsLoe(Integer paymentAmountsLoe) {
    return !Objects.isNull(paymentAmountsLoe) ? order.payment.paymentAmounts.loe(paymentAmountsLoe)
        : null;
  }

  private BooleanExpression paymentAmountsGoe(Integer paymentAmountsGoe) {
    return !Objects.isNull(paymentAmountsGoe) ? order.payment.paymentAmounts.goe(paymentAmountsGoe)
        : null;
  }

  private BooleanExpression paymentMeansIn(List<String> paymentMeansList) {
    return !CollectionUtils.isEmpty(paymentMeansList)
        ? order.payment.paymentMeans.in(
        paymentMeansList.stream()
            .map(PaymentMeans::fromValue)
            .collect(Collectors.toList()))
        : null;
  }

  private BooleanExpression orderStatusIn(List<String> orderStatusList) {
    return !CollectionUtils.isEmpty(orderStatusList)
        ? order.orderStatus.in(
        orderStatusList.stream()
            .map(OrderStatus::fromValue)
            .collect(Collectors.toList()))
        : null;
  }

  private BooleanExpression productNameContains(String productName) {
    return isNotBlank(productName) ? orderProduct.product.name.contains(productName) : null;
  }

  private BooleanExpression buyerEmailEq(String buyerEmail) {
    return isNotBlank(buyerEmail) ? appUserDetails.email.eq(buyerEmail) : null;
  }

  private BooleanExpression buyerIdEq(Long buyerId) {
    return buyerId != null ? buyer.id.eq(buyerId) : null;
  }


}

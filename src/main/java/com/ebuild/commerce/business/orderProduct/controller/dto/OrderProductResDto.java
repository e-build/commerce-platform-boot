package com.ebuild.commerce.business.orderProduct.controller.dto;

import com.ebuild.commerce.business.delivery.controller.dto.DeliveryResDto;
import com.ebuild.commerce.business.delivery.domain.entity.Delivery;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.business.product.controller.dto.CategoryResDto;
import com.ebuild.commerce.business.product.domain.entity.Category;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductResDto {

  private Long id;
  private Long orderId;
  private Long productId;
  private String productName;
  private CategoryResDto category;
  private Long normalAmount;
  private Long saleAmount;
  private Long quantity;
  private DeliveryResDto delivery;

  @QueryProjection
  public OrderProductResDto(Long id, Long orderId, Long productId, String productName,
      Category category, Long normalAmount, Long saleAmount,
      Long quantity, Delivery delivery) {
    this.id = id;
    this.orderId = orderId;
    this.productId = productId;
    this.productName = productName;
    this.category = CategoryResDto.of(category);
    this.normalAmount = normalAmount;
    this.saleAmount = saleAmount;
    this.quantity = quantity;
    this.delivery = DeliveryResDto.of(delivery);
  }

  @Builder
  public OrderProductResDto(Long id, Long productId, String productName,
      CategoryResDto category, Long normalAmount, Long saleAmount,
      Long quantity, DeliveryResDto delivery) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.category = category;
    this.normalAmount = normalAmount;
    this.saleAmount = saleAmount;
    this.quantity = quantity;
    this.delivery = delivery;
  }

  public static OrderProductResDto of(OrderProduct entity){
    Product product = entity.getProduct();
    return OrderProductResDto.builder()
        .id(entity.getId())
        .productId(product.getId())
        .productName(product.getName())
        .category(CategoryResDto.of(product.getCategory()))
        .normalAmount(product.getNormalAmount())
        .saleAmount(product.getSaleAmount())
        .quantity(entity.getQuantity())
        .delivery(DeliveryResDto.of(entity.getDelivery()))
        .build();
  }
}

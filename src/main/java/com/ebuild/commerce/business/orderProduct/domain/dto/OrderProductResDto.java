package com.ebuild.commerce.business.orderProduct.domain.dto;

import com.ebuild.commerce.business.delivery.domain.dto.DeliveryResDto;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.business.product.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductResDto {

  private Long id;
  private Long productId;
  private String productName;
  private String category;
  private Integer normalAmount;
  private Integer saleAmount;
  private Integer quantity;
  private DeliveryResDto delivery;

  @Builder
  public OrderProductResDto(Long id, Long productId, String productName, String category,
      Integer normalAmount, Integer saleAmount, Integer quantity,
      DeliveryResDto delivery) {
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
        .category(product.getCategory().value())
        .normalAmount(product.getNormalAmount())
        .saleAmount(product.getSaleAmount())
        .quantity(entity.getQuantity())
        .delivery(DeliveryResDto.of(entity.getDelivery()))
        .build();
  }
}

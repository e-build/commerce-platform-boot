package com.ebuild.commerce.business.orderProduct.controller.dto;

import com.ebuild.commerce.business.delivery.controller.dto.DeliveryResDto;
import com.ebuild.commerce.business.orderProduct.domain.entity.OrderProduct;
import com.ebuild.commerce.business.product.controller.dto.CategoryResDto;
import com.ebuild.commerce.business.product.domain.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductResDto {

  private Long id;
  private Long productId;
  private String productName;
  private List<CategoryResDto> categoryList;
  private Integer normalAmount;
  private Integer saleAmount;
  private Integer quantity;
  private DeliveryResDto delivery;

  @Builder
  public OrderProductResDto(Long id, Long productId, String productName,
      List<CategoryResDto> categoryList, Integer normalAmount, Integer saleAmount,
      Integer quantity, DeliveryResDto delivery) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.categoryList = categoryList;
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
        .categoryList(product
            .getCategoryList()
            .stream()
            .map(CategoryResDto::of)
            .collect(Collectors.toList()))
        .normalAmount(product.getNormalAmount())
        .saleAmount(product.getSaleAmount())
        .quantity(entity.getQuantity())
        .delivery(DeliveryResDto.of(entity.getDelivery()))
        .build();
  }
}

package com.ebuild.commerce.business.order.service;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.business.buyer.service.BuyerQueryService;
import com.ebuild.commerce.business.order.domain.dto.DirectOrderReqDto;
import com.ebuild.commerce.business.order.domain.dto.DirectOrderReqDto.OrderLineListDto;
import com.ebuild.commerce.business.order.domain.dto.OrderResDto;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.repository.JpaOrderRepository;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.config.security.annotation.IsAdmin;
import com.ebuild.commerce.config.security.annotation.IsSeller;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final BuyerQueryService buyerQueryService;
  private final JpaOrderRepository jpaOrderRepository;
  private final JpaProductRepository jpaProductRepository;

  public OrderResDto createOrder(String email, DirectOrderReqDto directOrderReqDto) {
    Buyer buyer = buyerQueryService.findByEmail(email);

    List<Product> products = jpaProductRepository
        .findByIds(
            directOrderReqDto.getOrderLineList()
                .stream()
                .map(OrderLineListDto::getProductId)
                .collect(Collectors.toList())
        );

    return OrderResDto.builder()
        .order(
            jpaOrderRepository.save(
                Order.createDirectOrder(buyer, products, directOrderReqDto.getBaseOrderInfo())
            )
        ).build();
  }
}

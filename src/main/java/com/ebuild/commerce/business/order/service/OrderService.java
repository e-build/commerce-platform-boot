package com.ebuild.commerce.business.order.service;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.service.BuyerQueryService;
import com.ebuild.commerce.business.order.controller.dto.DirectOrderReqDto;
import com.ebuild.commerce.business.order.controller.dto.DirectOrderReqDto.OrderLineDto;
import com.ebuild.commerce.business.order.controller.dto.OrderResDto;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.repository.JpaOrderRepository;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final BuyerQueryService buyerQueryService;
  private final OrderQueryService orderQueryService;
  private final JpaOrderRepository jpaOrderRepository;
  private final JpaProductRepository jpaProductRepository;

  @Transactional
  public OrderResDto createOrder(String email, DirectOrderReqDto directOrderReqDto) {
    Buyer buyer = buyerQueryService.findByEmail(email);

    List<Product> products = jpaProductRepository
        .findByIds(
            directOrderReqDto.getOrderLineList()
                .stream()
                .map(OrderLineDto::getProductId)
                .collect(Collectors.toList())
        );

    return OrderResDto.of(jpaOrderRepository.save(Order.createDirectOrder(buyer, products, directOrderReqDto)));
  }


}

package com.ebuild.commerce.business.order.service;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.order.domain.dto.DirectOrderReqDto;
import com.ebuild.commerce.business.order.domain.dto.OrderResDto;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.repository.JpaOrderRepository;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final JpaOrderRepository jpaOrderRepository;
  private final JpaProductRepository jpaProductRepository;

  public OrderResDto createOrder(Buyer buyer, DirectOrderReqDto directOrderReqDto) {

//    List<Product> products = jpaProductRepository.findByIdsIn(directOrderReqDto.getDirectOrderProductInfo().getProductIds());
//    Order order = Order.createDirectOrder(buyer, products, directOrderReqDto.getBaseOrderInfo());
    return null;
  }
}

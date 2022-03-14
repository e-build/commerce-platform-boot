package com.ebuild.commerce.business.cart.service;

import com.ebuild.commerce.business.cart.domain.dto.CartLineListPlusMinusReqDto;
import com.ebuild.commerce.business.cart.domain.dto.CartLineListPlusMinusReqDto.CartLineAddParam;
import com.ebuild.commerce.business.cart.domain.dto.CartResDto;
import com.ebuild.commerce.business.cart.domain.entity.Cart;
import com.ebuild.commerce.business.cart.repository.JpaCartRepository;
import com.ebuild.commerce.business.order.domain.dto.BaseOrderCreateReqDto;
import com.ebuild.commerce.business.order.domain.dto.OrderResDto;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.repository.JpaOrderRepository;
import com.ebuild.commerce.business.product.domain.entity.Product;
import com.ebuild.commerce.business.product.repository.JpaProductRepository;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

  private final JsonHelper jsonHelper;
  private final JpaCartRepository jpaCartRepository;
  private final JpaProductRepository jpaProductRepository;
  private final JpaOrderRepository jpaOrderRepository;

  @Transactional
  public void addCartLineList(Long cartId, CartLineListPlusMinusReqDto cartLineListPlusMinusReqDto)
  {
    Cart cart = findCartById(cartId);
    cartLineListPlusMinusReqDto.getCartLineList().forEach(cartLineDto -> {
      cart.addProduct(findProductById(cartLineDto), cartLineDto.getQuantity());
    });
  }

  @Transactional
  public void removeCartLineList(Long cartId, CartLineListPlusMinusReqDto cartLineListPlusMinusReqDto)
  {
    Cart cart = findCartById(cartId);
    cartLineListPlusMinusReqDto.getCartLineList().forEach(cartLineDto -> {
      cart.removeProduct(findProductById(cartLineDto), cartLineDto.getQuantity());
    });
  }

  @Transactional(readOnly = true)
  public CartResDto findById(Long cartId) {
    return CartResDto.builder()
        .cart(findCartById(cartId))
        .build();
  }

  @Transactional
  public OrderResDto createOrder(Long cartId, BaseOrderCreateReqDto baseOrderCreateReqDto)
  {
    Cart cart = findCartById(cartId);
    Order order = Order.createCartOrder(cart, baseOrderCreateReqDto);
    jpaOrderRepository.save(order);
    return OrderResDto.of(order);
  }

  private Product findProductById(CartLineAddParam cartLineDto)
  {
    return jpaProductRepository
        .findById(cartLineDto.getProductId())
        .orElseThrow(()->new NotFoundException("해당 상품은 존재하지 않습니다."));
  }

  private Cart findCartById(Long cartId) {
    return jpaCartRepository
        .findById(cartId)
        .orElseThrow(() -> new NotFoundException("장바구니가 존재하지 않습니다. cartId : [" + cartId + "]"));
  }
}

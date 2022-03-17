package com.ebuild.commerce.business.order.service;

import com.ebuild.commerce.business.order.domain.dto.OrderPagingListDto;
import com.ebuild.commerce.business.order.domain.dto.OrderQueryParamsDto;
import com.ebuild.commerce.business.order.domain.dto.OrderResDto;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.business.order.repository.JpaOrderRepository;
import com.ebuild.commerce.business.order.repository.OrderQueryRepository;
import com.ebuild.commerce.common.Paging;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

  private final JpaOrderRepository jpaOrderRepository;
  private final OrderQueryRepository orderQueryRepository;

  @Transactional(readOnly = true)
  public OrderPagingListDto search(String email, OrderQueryParamsDto orderQueryParamsDto) {
    Paging paging = orderQueryParamsDto.getPaging();
    paging.setTotalCount(orderQueryRepository.countBy(email, orderQueryParamsDto));

    List<Order> orderList = orderQueryRepository.searchBy(email, orderQueryParamsDto);

    return OrderPagingListDto.builder()
        .orders(
            orderList.stream()
            .map(OrderResDto::of)
            .collect(Collectors.toList()))
        .paging(paging)
        .sort(orderQueryParamsDto.getSort())
        .build();
  }


}

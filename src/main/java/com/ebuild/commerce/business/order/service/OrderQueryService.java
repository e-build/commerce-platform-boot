package com.ebuild.commerce.business.order.service;

import com.ebuild.commerce.business.order.controller.dto.OrderResDto;
import com.ebuild.commerce.business.order.controller.dto.OrderSearchCondition;
import com.ebuild.commerce.business.order.repository.JpaOrderRepository;
import com.ebuild.commerce.business.order.repository.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

  private final JpaOrderRepository jpaOrderRepository;
  private final OrderQueryRepository orderQueryRepository;

  @Transactional(readOnly = true)
  public Page<OrderResDto> search(OrderSearchCondition condition, Pageable pageable) {
    return orderQueryRepository.search(condition, pageable);
  }

}

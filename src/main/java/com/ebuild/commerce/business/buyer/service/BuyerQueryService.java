package com.ebuild.commerce.business.buyer.service;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.controller.dto.BuyerResDto;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BuyerQueryService {

  private final JpaBuyerRepository jpaBuyerRepository;

  public Buyer findByEmail(String email) {
    return jpaBuyerRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(email, "이메일"));
  }

  @Transactional(readOnly = true)
  public BuyerResDto findOneById(Long buyerId) {
    return BuyerResDto.builder()
        .buyer(jpaBuyerRepository
            .findById(buyerId)
            .orElseThrow(() -> new NotFoundException(String.valueOf(buyerId), "사용자"))
        ).build();
  }

}

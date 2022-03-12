package com.ebuild.commerce.business.buyer.service;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BuyerQueryService {

  private final JpaBuyerRepository jpaBuyerRepository;

  public Buyer findByEmail(String email) {
    return jpaBuyerRepository.findByEmail(email)
        .orElseThrow(()->new NotFoundException(email));
  }
}

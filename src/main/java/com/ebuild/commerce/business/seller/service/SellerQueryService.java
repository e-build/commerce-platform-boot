package com.ebuild.commerce.business.seller.service;

import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.business.seller.repository.JpaSellerRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SellerQueryService {

  private final JpaSellerRepository jpaSellerRepository;

  public Seller findByEmail(String email){
    return jpaSellerRepository.findByEmail(email)
        .orElseThrow(()-> new NotFoundException(email));
  }

}

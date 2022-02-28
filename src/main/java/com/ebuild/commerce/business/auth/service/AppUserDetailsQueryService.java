package com.ebuild.commerce.business.auth.service;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppUserDetailsQueryService {

  private final JpaAppUserDetailsRepository jpaAppUserDetailsRepository;

  public AppUserDetails findById(Long appUserDetailsId){
    return jpaAppUserDetailsRepository.findById(appUserDetailsId)
        .orElseThrow(()->new NotFoundException("["+appUserDetailsId+"] 사용자를 찾을 수 없습니다."));
  }


}

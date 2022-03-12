package com.ebuild.commerce.business.admin.service;

import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.admin.repository.JpaAdminRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminQueryService {

  private final JpaAdminRepository jpaAdminRepository;

  public Admin findByEmail(String email) {
    return jpaAdminRepository.findByEmail(email)
        .orElseThrow(()->new NotFoundException(email));
  }
}

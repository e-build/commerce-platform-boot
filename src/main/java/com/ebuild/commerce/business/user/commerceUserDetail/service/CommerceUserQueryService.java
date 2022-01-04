package com.ebuild.commerce.business.user.commerceUserDetail.service;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import com.ebuild.commerce.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommerceUserQueryService {

  private final CommerceUserDetailRepository commerceUserDetailRepository;

  public CommerceUserDetail findById(long commerceUserId) {
    return commerceUserDetailRepository
        .findByIdJoinFetch(commerceUserId)
        .orElseThrow(
            () -> new NotFoundException("사용자를 찾을 수 없습니다. commerceUserId : ["+commerceUserId+"]")
        );
  }
}

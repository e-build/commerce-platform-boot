package com.ebuild.commerce.config.security;

import com.ebuild.commerce.business.admin.repository.JpaAdminRepository;
import com.ebuild.commerce.business.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import com.ebuild.commerce.business.seller.repository.JpaSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommerceUserDetailService implements UserDetailsService {

  private final CommerceUserDetailRepository commerceUserDetailRepository;

  private final JpaAdminRepository jpaAdminRepository;
  private final JpaBuyerRepository jpaBuyerRepository;
  private final JpaSellerRepository jpaSellerRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return commerceUserDetailRepository
        .findOneByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("[" + email + "] 대한 계정은 존재하지 않습니다"));
  }

}

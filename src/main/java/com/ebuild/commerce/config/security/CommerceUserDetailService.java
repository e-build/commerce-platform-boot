package com.ebuild.commerce.config.security;

import com.ebuild.commerce.business.user.admin.repository.JpaAdminRepository;
import com.ebuild.commerce.business.user.buyer.repository.JpaBuyerRepository;
import com.ebuild.commerce.business.user.commerceUser.CommerceUserRepository;
import com.ebuild.commerce.business.user.seller.repository.JpaSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommerceUserDetailService implements UserDetailsService {

  private final CommerceUserRepository commerceUserRepository;

  private final JpaAdminRepository jpaAdminRepository;
  private final JpaBuyerRepository jpaBuyerRepository;
  private final JpaSellerRepository jpaSellerRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return commerceUserRepository
        .findOneByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("[" + email + "] 대한 계정은 존재하지 않습니다"));
  }

}

package com.ebuild.commerce.config.security;

import com.ebuild.commerce.business.user.commerceUserDetail.repository.CommerceUserDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommerceUserDetailService implements UserDetailsService {

  private final CommerceUserDetailRepository commerceUserDetailRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return commerceUserDetailRepository
        .findOneByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("[" + email + "] 대한 계정은 존재하지 않습니다"));
  }

}

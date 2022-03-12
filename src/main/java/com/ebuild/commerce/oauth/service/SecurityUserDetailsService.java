package com.ebuild.commerce.oauth.service;

import com.ebuild.commerce.business.auth.repository.JpaAppUserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

  private final JpaAppUserDetailsRepository jpaAppUserDetailsRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return jpaAppUserDetailsRepository
        .findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("[" + email + "] 대한 계정은 존재하지 않습니다"));
  }

}

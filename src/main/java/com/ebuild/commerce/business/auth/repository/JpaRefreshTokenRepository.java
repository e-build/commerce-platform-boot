package com.ebuild.commerce.business.auth.repository;

import com.ebuild.commerce.business.auth.domain.entity.AppRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository extends JpaRepository<AppRefreshToken, Long> {

  AppRefreshToken findByUserId(String userId);
  AppRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);

}

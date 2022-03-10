package com.ebuild.commerce.business.auth.repository;

import com.ebuild.commerce.business.auth.domain.entity.AppRefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository extends JpaRepository<AppRefreshToken, Long> {

  Optional<AppRefreshToken> findByUserId(String userId);
  AppRefreshToken findByUserIdAndRefreshToken(String userId, String refreshToken);

}

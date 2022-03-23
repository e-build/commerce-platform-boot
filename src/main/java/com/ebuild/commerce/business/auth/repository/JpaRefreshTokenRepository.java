package com.ebuild.commerce.business.auth.repository;

import com.ebuild.commerce.business.auth.domain.entity.AppRefreshToken;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaRefreshTokenRepository extends JpaRepository<AppRefreshToken, Long> {

  @Query(
      "select art "
      + "from AppRefreshToken art "
      + "join art.appUserDetails aud "
      + "where aud.email = :email "
  )
  Optional<AppRefreshToken> findByEmail(String email);
}

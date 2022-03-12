package com.ebuild.commerce.business.auth.repository;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaAppUserDetailsRepository extends JpaRepository<AppUserDetails, Long> {

  @Query(
      "select distinct aud "
      + "from AppUserDetails aud "
      + "join fetch aud.roleList userRoleList "
      + "join fetch userRoleList.role role "
      + "left outer join aud.buyer buyer "
      + "left outer join aud.seller seller "
      + "left outer join aud.admin admin "
      + "where aud.email = :email "
  )
  Optional<AppUserDetails> findByEmail(@Param("email") String email);
}

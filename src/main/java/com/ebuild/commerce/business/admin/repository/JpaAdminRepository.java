package com.ebuild.commerce.business.admin.repository;

import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAdminRepository extends JpaRepository<Admin, Long> {

  @Query("select admin from Admin admin where admin.appUserDetails.email = :email")
  Optional<Admin> findByEmail(@Param("email") String email);
}

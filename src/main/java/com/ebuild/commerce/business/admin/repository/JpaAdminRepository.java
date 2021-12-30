package com.ebuild.commerce.business.admin.repository;

import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAdminRepository extends JpaRepository<Admin, Long> {

  Optional<Admin> findOneByCommerceUserDetail(CommerceUserDetail commerceUserDetail);

}
package com.ebuild.commerce.business.user.commerceUserDetail.repository;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceUserDetailRepository extends JpaRepository<CommerceUserDetail, Long> {

  Optional<CommerceUserDetail> findOneByEmail(String email);

}

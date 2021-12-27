package com.ebuild.commerce.business.user.commerceUser;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceUserRepository extends JpaRepository<CommerceUserDetail, Long> {

  Optional<CommerceUserDetail> findOneByEmail(String email);

}

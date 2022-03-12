package com.ebuild.commerce.business.buyer.repository;

import com.ebuild.commerce.business.buyer.domain.Buyer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBuyerRepository extends JpaRepository<Buyer, Long> {

  @Query("select buyer from Buyer buyer where buyer.appUserDetails.email = :email")
  Optional<Buyer> findByEmail(@Param("email") String email);


}

package com.ebuild.commerce.business.seller.repository;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSellerRepository extends JpaRepository<Seller, Long> {

  Optional<Seller> findOneByAppUserDetails(AppUserDetails appUserDetails);

  @Query("select seller from Seller seller where seller.appUserDetails.email = :email")
  Optional<Seller> findByEmail(@Param("email") String email);

}

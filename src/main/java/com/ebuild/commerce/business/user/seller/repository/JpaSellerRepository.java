package com.ebuild.commerce.business.user.seller.repository;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.seller.domain.entity.Seller;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSellerRepository extends JpaRepository<Seller, Long> {

  Optional<Seller> findOneByCommerceUserDetail(CommerceUserDetail commerceUserDetail);

}

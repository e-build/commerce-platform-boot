package com.ebuild.commerce.business.user.buyer.repository;

import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBuyerRepository extends JpaRepository<Buyer, Long> {

  Optional<Buyer> findOneByCommerceUserDetail(CommerceUserDetail commerceUserDetail);

}

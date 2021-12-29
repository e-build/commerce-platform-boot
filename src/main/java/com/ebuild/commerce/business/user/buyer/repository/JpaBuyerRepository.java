package com.ebuild.commerce.business.user.buyer.repository;

import com.ebuild.commerce.business.user.buyer.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBuyerRepository extends JpaRepository<Buyer, Long> {

}

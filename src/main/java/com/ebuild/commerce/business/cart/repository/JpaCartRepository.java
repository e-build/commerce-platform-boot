package com.ebuild.commerce.business.cart.repository;

import com.ebuild.commerce.business.cart.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCartRepository extends JpaRepository<Cart, Long> {

}

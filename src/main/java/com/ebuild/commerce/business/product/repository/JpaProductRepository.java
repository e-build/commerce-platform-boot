package com.ebuild.commerce.business.product.repository;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.product.domain.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByCompanyAndName(Company company, String name);

  Page<Product> findAllByCompanyAndNameContaining(Company company, String name, Pageable pageable);

  Page<Product> findAll(Specification<Product> spec, Pageable pageable);

  @Query("select product"
      + " from Product product"
      + " where product.id in :productIds")
  List<Product> findByIds(@Param("productIds") List<Long> productIds);
}

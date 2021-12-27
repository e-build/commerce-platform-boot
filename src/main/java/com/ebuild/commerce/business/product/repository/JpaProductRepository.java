package com.ebuild.commerce.business.product.repository;

import com.ebuild.commerce.business.company.domain.entity.Company;
import com.ebuild.commerce.business.product.domain.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByCompanyAndName(Company company, String name);

  Page<Product> findAllByCompanyAndNameContaining(Company company, String name, Pageable pageable);

  Page<Product> findAll(Specification<Product> spec, Pageable pageable);

}

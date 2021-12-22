package com.ebuild.commerce.business.product.repository;

import com.ebuild.commerce.business.company.domain.Company;
import com.ebuild.commerce.business.product.domain.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

  public Optional<Product> findByCompanyAndName(Company company, String name);


}

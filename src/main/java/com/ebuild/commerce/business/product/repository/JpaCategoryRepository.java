package com.ebuild.commerce.business.product.repository;

import com.ebuild.commerce.business.product.domain.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findByIdIn(List<Long> categoryIdList);
}

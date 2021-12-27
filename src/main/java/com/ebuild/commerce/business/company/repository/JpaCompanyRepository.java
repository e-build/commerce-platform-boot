package com.ebuild.commerce.business.company.repository;

import com.ebuild.commerce.business.company.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<Company, Long> {

}

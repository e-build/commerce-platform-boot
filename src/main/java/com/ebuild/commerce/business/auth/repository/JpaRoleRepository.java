package com.ebuild.commerce.business.auth.repository;

import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import com.ebuild.commerce.business.auth.domain.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRoleRepository extends JpaRepository<Role, Long> {

  List<Role> findAllByNameIn(List<RoleType> roleTypes);

}

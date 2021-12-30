package com.ebuild.commerce.business.user.role.repository;

import com.ebuild.commerce.business.user.role.CommerceRole;
import com.ebuild.commerce.business.user.role.domain.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository extends JpaRepository<Role, Long> {

  List<Role> findAllByNameIn(List<CommerceRole> commerceRoles);

}

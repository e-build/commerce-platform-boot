package com.ebuild.commerce.business.user.commerceUserDetail.repository;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommerceUserDetailRepository extends JpaRepository<CommerceUserDetail, Long> {

  @Query("select distinct cud"
      + " from CommerceUserDetail cud"
      + " join fetch cud.roleList userRoleList "
      + " join fetch userRoleList.role role "
      + " left join fetch cud.buyer buyer "
      + " left join fetch cud.seller seller "
      + " left join fetch cud.admin admin "
      + " where cud.email = :email")
  Optional<CommerceUserDetail> findOneByEmail(@Param("email") String email);

  @Query("select distinct cud"
      + " from CommerceUserDetail cud"
      + " join fetch cud.roleList userRoleList "
      + " join fetch userRoleList.role role "
      + " left join fetch cud.buyer buyer "
      + " left join fetch cud.seller seller "
      + " left join fetch cud.admin admin "
      + " where cud.id = :commerceUserDetailId")
  Optional<CommerceUserDetail> findByIdJoinFetch(@Param("commerceUserDetailId")Long commerceUserDetailId);

}

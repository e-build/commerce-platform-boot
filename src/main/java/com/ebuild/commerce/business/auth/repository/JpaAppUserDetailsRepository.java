package com.ebuild.commerce.business.auth.repository;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAppUserDetailsRepository extends JpaRepository<AppUserDetails, Long> {

    @Query("select distinct cud"
        + " from AppUserDetails cud"
        + " join fetch cud.roleList userRoleList "
        + " join fetch userRoleList.role role "
        + " left join fetch cud.buyer buyer "
        + " left join fetch cud.seller seller "
        + " left join fetch cud.admin admin "
        + " where cud.email = :email")
    Optional<AppUserDetails> findOneByEmail(@Param("email") String email);

    @Query("select distinct cud"
        + " from AppUserDetails cud"
        + " join fetch cud.roleList userRoleList "
        + " join fetch userRoleList.role role "
        + " left join fetch cud.buyer buyer "
        + " left join fetch cud.seller seller "
        + " left join fetch cud.admin admin "
        + " where cud.id = :commerceUserDetailId")
    Optional<AppUserDetails> findByIdJoinFetch(@Param("commerceUserDetailId")Long commerceUserDetailId);

}

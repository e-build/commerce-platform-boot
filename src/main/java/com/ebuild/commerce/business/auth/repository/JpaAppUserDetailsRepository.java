package com.ebuild.commerce.business.auth.repository;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAppUserDetailsRepository extends JpaRepository<AppUserDetails, Long> {

    @Query("select distinct aud"
        + " from AppUserDetails aud"
        + " join fetch aud.roleList userRoleList "
        + " join fetch userRoleList.role role "
        + " left join fetch aud.buyer buyer "
        + " left join fetch aud.seller seller "
        + " left join fetch aud.admin admin "
        + " where aud.email = :email")
    Optional<AppUserDetails> findOneByEmail(@Param("email") String email);

    @Query("select distinct aud"
        + " from AppUserDetails aud"
        + " join fetch aud.roleList userRoleList "
        + " join fetch userRoleList.role role "
        + " left join fetch aud.buyer buyer "
        + " left join fetch aud.seller seller "
        + " left join fetch aud.admin admin "
        + " where aud.id = :appUserDetailsId")
    Optional<AppUserDetails> findByIdJoinFetch(@Param("appUserDetailsId")Long appUserDetailsId);

}

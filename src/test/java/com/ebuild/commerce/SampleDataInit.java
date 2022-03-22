package com.ebuild.commerce;

import com.ebuild.commerce.business.auth.domain.entity.Role;
import com.ebuild.commerce.business.auth.domain.entity.RoleType;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class SampleDataInit {

  private final Initializer initializer;

  @PostConstruct
  public void init() {
    initializer.roleInit();
  }

  @Component
  @RequiredArgsConstructor
  public static class Initializer{

    private final EntityManager em;

    @Transactional
    public void roleInit() {
      /*****************
       **** 권한 생성 ****
       *****************/
      Role buyerRole = new Role(1L, RoleType.BUYER);
      Role sellerRole = new Role(2L, RoleType.SELLER);
      Role adminRole = new Role(3L, RoleType.ADMIN);

      em.persist(buyerRole);
      em.persist(sellerRole);
      em.persist(adminRole);

      em.flush();
      em.clear();
    }

  }



}

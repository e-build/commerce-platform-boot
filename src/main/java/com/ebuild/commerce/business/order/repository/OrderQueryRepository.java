package com.ebuild.commerce.business.order.repository;

import com.ebuild.commerce.business.order.controller.dto.OrderQueryParamsDto;
import com.ebuild.commerce.business.order.domain.entity.Order;
import com.ebuild.commerce.common.Paging;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

  private final EntityManager em;

  public Long countBy(String email, OrderQueryParamsDto params) {
    return em.createQuery(
            "select count(o) "
                + "from Order o "
                + "join o.buyer buyer " + "join buyer.appUserDetails appUserDetails "
                + "where 1 = 1 "
                + eqEmail(email)
                + "order by " + params.getSort().toString() + " ", Long.class)
        .setParameter("email", email)
        .getSingleResult();
  }

  public List<Order> searchBy(String email, OrderQueryParamsDto params) {
    Paging paging = params.getPaging();
    return em.createQuery(
            "select o "
                + "from Order o "
                + "join fetch o.buyer buyer "
                + "join fetch buyer.appUserDetails appUserDetails "
                + "where 1 = 1 "
                + eqEmail(email)
                + "order by " + params.getSort().toString() + " ", Order.class)
        .setParameter("email", email)
        .setFirstResult(paging.offset())
        .setMaxResults(paging.getSize())
        .getResultList();
  }

  private String eqEmail(String email) {
    if (email != null) {
      return "and appUserDetails.email = :email ";
    }
    return "";
  }

}

package com.ebuild.commerce.business.order.repository;


import com.ebuild.commerce.business.order.domain.dto.OrderQueryParamsDto;
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

  public int countBy(String email, OrderQueryParamsDto params){
    return em.createQuery(orderListPagingQuery(email, params, "select count o "), Integer.class)
        .getSingleResult();
  }

  private String orderListPagingQuery(String email, OrderQueryParamsDto params, String s) {
    return s
        + "from Order o "
        + "join fetch o.buyer buyer "
        + "join fetch buyer.appUserDetails appUserDetails "
        + "where 1 = 1 "
        + eqEmail(email)
        + "order by " + params.getSort().toString() + " ";
  }

  public List<Order> searchByPagingAndSorting(String email, OrderQueryParamsDto params) {
    Paging paging = params.getPaging();

    return em.createQuery(
            orderListPagingQuery(email, params, "select o "), Order.class)
        .setParameter("email", email)
        .setFirstResult(paging.offset())
        .setMaxResults(paging.getPage() * paging.getSize())
        .getResultList();
  }

  private String eqEmail(String email) {
    if( email != null)
      return "and appUserDetails.email = :email ";
    return "";
  }

}

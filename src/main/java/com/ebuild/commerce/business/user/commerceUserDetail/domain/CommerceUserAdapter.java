package com.ebuild.commerce.business.user.commerceUserDetail.domain;

import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import lombok.Getter;

@Getter
public class CommerceUserAdapter {

  private final CommerceUserDetail commerceUserDetail;

  public CommerceUserAdapter(CommerceUserDetail commerceUserDetail){
    this.commerceUserDetail = commerceUserDetail;
  }

  public Buyer getBuyer(){
    return commerceUserDetail.getBuyer();
  }

  public Admin getAdmin(){
    return commerceUserDetail.getAdmin();
  }

  public Seller getSeller(){
    return commerceUserDetail.getSeller();
  }


}

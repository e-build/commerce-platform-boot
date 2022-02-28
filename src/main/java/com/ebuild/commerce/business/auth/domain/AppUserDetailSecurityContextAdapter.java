package com.ebuild.commerce.business.auth.domain;

import com.ebuild.commerce.business.admin.domain.entity.Admin;
import com.ebuild.commerce.business.buyer.domain.Buyer;
import com.ebuild.commerce.business.seller.domain.entity.Seller;
import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import lombok.Getter;

@Getter
public class AppUserDetailSecurityContextAdapter {

  private final AppUserDetails appUserDetails;

  public AppUserDetailSecurityContextAdapter(AppUserDetails appUserDetails){
    this.appUserDetails = appUserDetails;
  }

  public Buyer getBuyer(){
    return appUserDetails.getBuyer();
  }

  public Admin getAdmin(){
    return appUserDetails.getAdmin();
  }

  public Seller getSeller(){
    return appUserDetails.getSeller();
  }


}

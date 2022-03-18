package com.ebuild.commerce.business.product.domain.entity;

import com.ebuild.commerce.business.auth.domain.entity.AppUserRole;
import com.google.common.collect.Lists;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String code;
  private String superCode;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  private List<Product> productList = Lists.newArrayList();

  @Builder
  public Category(Long id, String name, String code, String superCode) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.superCode = superCode;
  }

}

package com.ebuild.commerce.business.user.role.domain;

import com.ebuild.commerce.business.user.role.CommerceRole;
import com.google.common.collect.Lists;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private CommerceRole name;

  @OneToMany(mappedBy = "role")
  private List<CommerceUserRole> commerceUserRoleList = Lists.newArrayList();

  public Role(Long id, CommerceRole name){
    this.name = name;
  }

}

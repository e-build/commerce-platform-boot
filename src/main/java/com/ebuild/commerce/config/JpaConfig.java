package com.ebuild.commerce.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@RequiredArgsConstructor
@Configuration
public class JpaConfig {

  private final EntityManager em;

  @Bean
  public JPAQueryFactory jpaQueryFactory(){
   return new JPAQueryFactory(em);
  }

}

package com.ebuild.commerce;

import com.ebuild.commerce.config.security.properties.AppProperties;
import com.ebuild.commerce.config.security.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties({
    CorsProperties.class,
    AppProperties.class
})
@EnableJpaAuditing
@SpringBootApplication
public class CommerceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CommerceApplication.class, args);

    System.out.println("#########################################");
    System.out.println("################ 빌드 성공 ! ################");
    System.out.println("#########################################");

  }

}

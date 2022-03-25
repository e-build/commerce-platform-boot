package com.ebuild.commerce.business.test;

import com.ebuild.commerce.common.http.CommonResponse;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class Controller {

  private String serverHash;

  @PostConstruct
  public void setUpServerHash(){
    serverHash = LocalDateTime.now().toString();
  }

  @GetMapping("/host")
  public ResponseEntity<CommonResponse> getHost(){
    return ResponseEntity.ok(CommonResponse.OK("hash", serverHash));
  }

}

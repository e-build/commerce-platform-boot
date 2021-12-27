package com.ebuild.commerce.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class BcryptTest {

  @Test
  void encrypt(){
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    log.info(bCryptPasswordEncoder.encode("1234qwer!@#$"));
  }

}

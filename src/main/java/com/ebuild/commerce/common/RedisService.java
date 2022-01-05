package com.ebuild.commerce.common;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

  private final StringRedisTemplate redisTemplate;

  public void setData(String key, String value){
    redisTemplate.opsForValue().set(key, value);
  }

  public void setDataWithDuration(String key, String value){
    redisTemplate.opsForValue().set(key, value, 60L, TimeUnit.SECONDS);
  }

  public String getData(String key){
    return redisTemplate.opsForValue().get(key);
  }


}

package com.ebuild.commerce.common;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.config.security.SecurityConstants;
import java.time.Duration;
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
    redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(60L));
  }

  public void setRefreshToken(CommerceUserDetail commerceUserDetail, String token){
    redisTemplate.opsForValue().set(
        SecurityConstants.REDIS_REFRESH_TOKEN_KEY + commerceUserDetail.getId()
        , token
        , Duration.ofDays(7)
    );
  }

  public String getRefreshToken(String userId){
    return redisTemplate.opsForValue().get(userId);
  }

  public String getData(String key){
    return redisTemplate.opsForValue().get(key);
  }


}

package com.ebuild.commerce.common;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.SecurityConstants;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

  private final StringRedisTemplate redisTemplate;
  private final JsonHelper jsonHelper;

  public void setData(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public String getData(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void setRefreshToken(AppUserDetails appUserDetails, String token) {
    redisTemplate.opsForValue().set(
        SecurityConstants.REDIS_REFRESH_TOKEN_KEY + appUserDetails.getEmail()
        , token
        , Duration.ofDays(7)
    );
  }

  public String getRefreshToken(String email) {
    return redisTemplate.opsForValue().get(SecurityConstants.REDIS_REFRESH_TOKEN_KEY + email);
  }

  public void removeRefreshToken(AppUserDetails appUserDetails) {
    redisTemplate.delete(
        SecurityConstants.REDIS_REFRESH_TOKEN_KEY + appUserDetails.getEmail()
    );
  }

  public void removeUser(AppUserDetails appUserDetails) {
    redisTemplate.delete(String.valueOf(appUserDetails.getEmail()));
  }

  public void setUser(AppUserDetails appUserDetails) {
    redisTemplate.opsForValue()
        .set(String.valueOf(appUserDetails.getEmail()), jsonHelper.serialize(appUserDetails));
  }

//  public AppUserDetails getUser(String email) {
//    String cachedData = Optional
//        .ofNullable(getData("USER_" + email))
//        .orElse(null);
//
//    if (!isNull(cachedData))
//      return jsonHelper.deserialize(cachedData, AppUserDetails.class);
//
//    AppUserDetails appUserDetails = appUserDetailsQueryService.findByEmail(email);
//    setUser(appUserDetails);
//    return appUserDetails;
//  }

}

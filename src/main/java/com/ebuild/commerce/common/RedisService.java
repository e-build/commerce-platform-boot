package com.ebuild.commerce.common;

import static java.util.Objects.isNull;

import com.ebuild.commerce.business.auth.domain.entity.AppUserDetails;
import com.ebuild.commerce.business.auth.service.AppUserDetailsQueryService;
import com.ebuild.commerce.config.JsonHelper;
import com.ebuild.commerce.config.security.SecurityConstants;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisService {

  private final StringRedisTemplate redisTemplate;
  private final JsonHelper jsonHelper;
  private final AppUserDetailsQueryService appUserDetailsQueryService;

  public void setData(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public String getData(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void setRefreshToken(AppUserDetails appUserDetails, String token) {
    redisTemplate.opsForValue().set(
        SecurityConstants.REDIS_REFRESH_TOKEN_KEY + appUserDetails.getId()
        , token
        , Duration.ofDays(7)
    );
  }

  public String getRefreshToken(Long commerceUserDetailId) {
    return redisTemplate.opsForValue().get(SecurityConstants.REDIS_REFRESH_TOKEN_KEY + commerceUserDetailId);
  }

  public void removeRefreshToken(AppUserDetails appUserDetails) {
    redisTemplate.delete(
        SecurityConstants.REDIS_REFRESH_TOKEN_KEY + appUserDetails.getId()
    );
  }

  public void removeUser(AppUserDetails appUserDetails) {
    redisTemplate.delete(String.valueOf(appUserDetails.getId()));
  }

  public void setUser(AppUserDetails appUserDetails) {
    redisTemplate.opsForValue()
        .set(String.valueOf(appUserDetails.getId()), jsonHelper.serialize(appUserDetails));
  }

  public AppUserDetails getUser(String email) {
    String cachedData = Optional
        .ofNullable(getData("USER_" + email))
        .orElse(null);

    if (!isNull(cachedData))
      return jsonHelper.deserialize(cachedData, AppUserDetails.class);

    AppUserDetails appUserDetails = appUserDetailsQueryService.findByEmail(email);
    setUser(appUserDetails);
    return appUserDetails;
  }

}

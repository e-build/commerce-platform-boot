package com.ebuild.commerce.common;

import static java.util.Objects.isNull;

import com.ebuild.commerce.business.user.commerceUserDetail.domain.entity.CommerceUserDetail;
import com.ebuild.commerce.business.user.commerceUserDetail.service.CommerceUserQueryService;
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
  private final CommerceUserQueryService commerceUserQueryService;

  public void setData(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
  }

  public String getData(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  public void setRefreshToken(CommerceUserDetail commerceUserDetail, String token) {
    redisTemplate.opsForValue().set(
        SecurityConstants.REDIS_REFRESH_TOKEN_KEY + commerceUserDetail.getId()
        , token
        , Duration.ofDays(7)
    );
  }

  public String getRefreshToken(Long commerceUserDetailId) {
    return redisTemplate.opsForValue().get(SecurityConstants.REDIS_REFRESH_TOKEN_KEY + commerceUserDetailId);
  }

  public void removeRefreshToken(CommerceUserDetail commerceUserDetail) {
    redisTemplate.delete(
        SecurityConstants.REDIS_REFRESH_TOKEN_KEY + commerceUserDetail.getId()
    );
  }

  public void removeUserDetail(CommerceUserDetail commerceUserDetail) {
    redisTemplate.delete(String.valueOf(commerceUserDetail.getId()));
  }

  public void setUserDetail(CommerceUserDetail commerceUserDetail) {
    redisTemplate.opsForValue()
        .set(String.valueOf(commerceUserDetail.getId()), jsonHelper.serialize(commerceUserDetail));
  }

  public CommerceUserDetail getCommerceUserDetail(Long commerceUserDetailId) {
    String cachedData = Optional
        .ofNullable(getData(String.valueOf(commerceUserDetailId)))
        .orElse(null);

    if (!isNull(cachedData))
      return jsonHelper.deserialize(cachedData, CommerceUserDetail.class);

    CommerceUserDetail commerceUserDetail = commerceUserQueryService.findById(commerceUserDetailId);
    setUserDetail(commerceUserDetail);
    return commerceUserDetail;
  }
}

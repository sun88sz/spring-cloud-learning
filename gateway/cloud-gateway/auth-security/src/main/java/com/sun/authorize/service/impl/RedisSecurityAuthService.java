package com.sun.authorize.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.authorize.entity.IResource;
import com.sun.authorize.entity.IRole;
import com.sun.authorize.entity.IUser;
import com.sun.authorize.entity.impl.Auth;
import com.sun.authorize.entity.impl.BaseResource;
import com.sun.authorize.entity.impl.BaseRole;
import com.sun.authorize.service.SecurityAuthService;
import com.sun.authorize.service.SecurityPermissionService;
import com.sun.authorize.service.SecurityRoleService;
import com.sun.authorize.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sun.config.AuthorizationConst.REDIS_AUTH_KEY;

/**
 * Auth 放入 Redis
 *
 * @author SunChenjie
 * @version 1.0
 */
@Slf4j
public class RedisSecurityAuthService implements SecurityAuthService {

    private String systemName;

    ObjectMapper mapper = new ObjectMapper();

    // jwt 过期时间
    private long jwtExpire;

    private StringRedisTemplate redisTemplate;
    private SecurityUserService securityUserService;
    private SecurityRoleService securityRoleService;
    private SecurityPermissionService securityPermissionService;

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public void setJwtExpire(long jwtExpire) {
        this.jwtExpire = jwtExpire;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setSecurityUserService(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    public void setSecurityPermissionService(SecurityPermissionService securityPermissionService) {
        this.securityPermissionService = securityPermissionService;
    }

    public void setSecurityRoleService(SecurityRoleService securityRoleService) {
        this.securityRoleService = securityRoleService;
    }

    @Override
    public void removeCacheAllUsers() {
        Set<String> keys = redisTemplate.keys(REDIS_AUTH_KEY + ":" + systemName + "*");
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public void removeCacheUserById(Long userId) {
        String userKey = REDIS_AUTH_KEY + ":" + systemName + ":" + userId;
        redisTemplate.delete(userKey);
    }

    @Override
    public Auth loadAuthByUserId(Long userId) {
        String userKey = REDIS_AUTH_KEY + ":" + systemName + ":" + userId;

        // token没过期，加载redis中的用户信息，如果redis中没有加载数据库
        String authStr = redisTemplate.opsForValue().get(userKey);
        if (StringUtils.isNotEmpty(authStr)) {
            Auth auth = null;
            try {
                auth = mapper.readValue(authStr, Auth.class);
            } catch (IOException e) {
                log.error("auth string 转换错误", e);
            }
            if (auth != null) {
                redisTemplate.expire(userKey, jwtExpire, TimeUnit.SECONDS);
                // 返回缓存中的数据
                return auth;
            }
        }

        // 如果缓存中没有，从数据库中加载
        Auth auth = loadNewAuthByUserId(userId);
        if (auth != null) {
            try {
                IUser user = auth.getUser();
                if (user != null) {
                    user.setPassword("------");
                }
                authStr = mapper.writeValueAsString(auth);
                redisTemplate.opsForValue().set(userKey, authStr, jwtExpire, TimeUnit.SECONDS);
            } catch (JsonProcessingException e) {
                log.error("auth string 转换错误", e);
            }
        }

        return auth;
    }

    @Override
    public Auth loadNewAuthByUserId(Long userId) {
        // 如果redis中没有，从数据库中load
        IUser user = securityUserService.selectById(userId);
        List<IRole> roles = securityRoleService.selectRoleByUser(userId);
        List<IResource> resources = securityPermissionService.selectResourceByUser(userId);
        Auth auth = new Auth(user);

        if (CollectionUtils.isNotEmpty(roles)) {
            List<BaseRole> rs = roles.stream().map(
                    r -> {
                        BaseRole role = new BaseRole();
                        role.setCode(r.getCode());
                        return role;
                    }
            ).collect(Collectors.toList());
            auth.setRoles(rs);
        }

        if (CollectionUtils.isNotEmpty(resources)) {
            List<BaseResource> rs = resources.stream().map(
                    r -> {
                        BaseResource res = new BaseResource();
                        res.setCode(r.getCode());
                        return res;
                    }
            ).collect(Collectors.toList());
            auth.setResources(rs);
        }
        return auth;
    }

}

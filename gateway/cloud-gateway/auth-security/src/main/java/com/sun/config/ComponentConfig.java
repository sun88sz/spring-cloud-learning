package com.sun.config;

import com.sun.authorize.entity.IResource;
import com.sun.authorize.service.*;
import com.sun.authorize.service.impl.RedisSecurityAuthService;
import com.sun.login.filter.JWTTokenService;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import java.util.Map;


/**
 * 组件配置
 *
 * @author SunChenjie
 * @version 1.0
 */
@Configuration
public class ComponentConfig {

    @Value("${auth.jwt.expire:3600}")
    private long jwtExpire;
    @Value("${auth.jwt.secret:JDuwadvf89sEWdQfx}")
    private String jwtSecret;
    @Value("${auth.jwt.tokenName:token}")
    private String tokenName;

    @Value("spring.application.name")
    private String systemName;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private SecurityRoleService securityRoleService;
    @Autowired
    private SecurityPermissionService securityPermissionService;

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    @ConditionalOnMissingBean(SecurityAuthService.class)
    public SecurityAuthService loadAuthService() {
        RedisSecurityAuthService redisLoadAuthService = new RedisSecurityAuthService();
        redisLoadAuthService.setRedisTemplate(stringRedisTemplate);
        redisLoadAuthService.setJwtExpire(jwtExpire);
        redisLoadAuthService.setSecurityUserService(securityUserService);
        redisLoadAuthService.setSecurityPermissionService(securityPermissionService);
        redisLoadAuthService.setSecurityRoleService(securityRoleService);
        redisLoadAuthService.setSystemName(systemName);
        return redisLoadAuthService;
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    public JWTTokenService jwtTokenService() {
        JWTTokenService jwtTokenService = new JWTTokenService(jwtExpire, createJwtKey(), tokenName);
        return jwtTokenService;
    }

    /**
     * @return jwt 秘钥 key
     */
    private Key createJwtKey() {
        Key key;
        Assert.hasLength(jwtSecret, "jwtSecret 不能为空");
        byte[] bytes = jwtSecret.getBytes(StandardCharsets.UTF_8);

        byte[] byteKey = new byte[32];
        if (bytes.length > 32) {
            System.arraycopy(bytes, 0, byteKey, 0, 32);
        } else {
            System.arraycopy(bytes, 0, byteKey, 0, bytes.length);
            for (int i = bytes.length; i < 32; i++) {
                byteKey[i] = (byte) (i % 32);
            }
        }
        key = Keys.hmacShaKeyFor(byteKey);
        return key;
    }

    /**
     * 默认空实现
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SecurityPermissionService.class)
    public SecurityPermissionService securityPermissionService() {
        return new SecurityPermissionService() {
            @Override
            public List<IResource> selectResourceByUser(Long userId) {
                return null;
            }

            @Override
            public Map<String, List<IResource>> selectAllMapping() {
                return null;
            }
        };
    }


    @Bean
    @ConditionalOnMissingBean(SecurityRoleService.class)
    public SecurityRoleService securityRoleService() {
        return userId -> null;
    }


    @Bean
    @ConditionalOnMissingBean(LoginAroundInterceptService.class)
    public LoginAroundInterceptService loginAroundInterceptService() {
        return new LoginAroundInterceptService() {
            @Override
            public void afterLoginSuccessful(Long userId) {

            }

            @Override
            public void afterLoginFailed(String username) {

            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(SecurityNoLoginUrlService.class)
    public SecurityNoLoginUrlService securityNoLoginUrlService() {
        return () -> null;
    }


}

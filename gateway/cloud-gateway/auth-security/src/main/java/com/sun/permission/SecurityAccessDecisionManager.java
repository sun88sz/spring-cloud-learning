package com.sun.permission;

import com.sun.authorize.entity.impl.Auth;
import com.sun.authorize.entity.impl.BaseResource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SunChenjie
 * @version 1.0
 */
public class SecurityAccessDecisionManager implements AccessDecisionManager {

    /**
     * decide 方法是判定是否拥有权限的决策方法，
     * authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }
        Auth auth = (Auth) authentication;

        // 用户有的权限
        List<BaseResource> resources = auth.getResources();

        // 只要用户持有的权限和url所需的权限有一个匹配即可
        if (CollectionUtils.isNotEmpty(configAttributes)) {
            if (CollectionUtils.isNotEmpty(resources)) {
                Set<String> codes = resources.stream().map(BaseResource::getCode).collect(Collectors.toSet());

                for (ConfigAttribute c : configAttributes) {
                    if (codes.contains(c.getAttribute())) {
                        return;
                    }
                }
            }
            throw new AccessDeniedException("无权限访问");
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

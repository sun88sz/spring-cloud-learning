package com.sun.authorize.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

/**
 * 用户授权信息
 * 当前登录用户的权限信息,包括用户的基本信息,角色,权限集合等常用信息<br>
 *
 * @author Sunchenjie
 * @version 1.0
 */
public interface IAuth extends Authentication {

    /**
     * @return 用户信息
     */
    <T extends IUser> T getUser();

    /**
     * @return 用户持有的角色集合
     */
    <T extends IRole> List<T> getRoles();

    /**
     * @return 用户持有的资源集合
     */
    <T extends IResource> List<T> getResources();

    /**
     * 根据id获取角色,角色不存在则返回null
     *
     * @param roleCode 角色code
     * @return 角色信息
     */
    default Optional<IRole> getRole(String roleCode) {
        if (StringUtils.isEmpty(roleCode)) {
            return Optional.empty();
        }
        List<IRole> roles = getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            return Optional.empty();
        }
        return roles.stream().filter(r -> r.getCode().equals(roleCode)).findAny();
    }


    /**
     * @param code 角色code
     * @return 是否拥有某个角色
     */
    default boolean hasRole(String code) {
        return getRole(code).isEmpty();
    }

    /**
     * 根据权限id获取权限信息,权限不存在则返回null
     *
     * @param resourceCode 权限id
     * @return 权限信息
     */
    default Optional<IResource> getResource(String resourceCode) {
        if (StringUtils.isEmpty(resourceCode)) {
            return Optional.empty();
        }
        List<IResource> resources = getResources();
        if (CollectionUtils.isEmpty(resources)) {
            return Optional.empty();
        }
        return resources.stream().filter(r -> r.getCode().equals(resourceCode)).findAny();
    }

    @JsonIgnore
    @Override
    default Object getPrincipal() {
        return getUser();
    }

}

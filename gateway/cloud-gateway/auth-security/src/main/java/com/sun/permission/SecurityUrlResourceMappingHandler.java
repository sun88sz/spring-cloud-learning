package com.sun.permission;

import com.google.common.collect.Lists;
import com.sun.authorize.entity.IResource;
import com.sun.authorize.service.SecurityPermissionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * url所需的权限映射的resource
 *
 * @author SunChenjie
 * @version 1.0
 */
public class SecurityUrlResourceMappingHandler implements FilterInvocationSecurityMetadataSource {

    private SecurityPermissionService securityPermissionService;

    public void setSecurityPermissionService(SecurityPermissionService securityPermissionService) {
        this.securityPermissionService = securityPermissionService;
    }

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine() {
        map = new HashMap<>();
        Map<String, List<IResource>> urlResourceMapping = securityPermissionService.selectAllMapping();
        if (MapUtils.isNotEmpty(urlResourceMapping)) {
            urlResourceMapping.entrySet().stream().forEach(
                    m -> {
                        List<IResource> resources = m.getValue();
                        Collection<ConfigAttribute> configs = Lists.newArrayList();
                        if (CollectionUtils.isNotEmpty(resources)) {
                            configs = resources.stream().map(r -> new SecurityConfig(r.getCode())).distinct().collect(Collectors.toList());
                        }
                        //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value
                        map.put(m.getKey(), configs);
                    }
            );
        }
    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，
     * 如果在权限表中，则返回给 decide 方法，
     * 用来判定用户是否有此权限。如果不在权限表中则放行。
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }

        FilterInvocation invocation = (FilterInvocation) object;
        //获取请求的url方法
        String url = invocation.getRequestUrl();
//        if (url.contains("/mobile/")) {
//            url = url.substring(url.indexOf("/mobile/"), url.length());
//        } else if (url.contains("/web/")) {
//            url = url.substring(url.indexOf("/web/"), url.length());
//        }
//        if (url.contains("?")) {
//            url = url.substring(0, url.lastIndexOf("?"));
//        }

//        //object 中包含用户请求的request 信息
//        HttpServletRequest request = invocation.getHttpRequest();
        if(url.startsWith("/cloud/")) {
            return null;
        }
        AntPathMatcher matcher = new AntPathMatcher();
        Set<String> urls = map.keySet();
        for (String resUrl : urls) {
            if (matcher.match("/**" + resUrl, url)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}

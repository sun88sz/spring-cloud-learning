package com.sun.login.provider;

import com.sun.authorize.entity.impl.Auth;
import com.sun.authorize.entity.impl.BaseUser;
import com.sun.authorize.service.SecurityAuthService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户登录验证器
 * 仅仅只是重载 createSuccessAuthentication 方法
 * 用于登录后加载权限
 *
 * @author Sunchenjie
 * @version 1.0
 */
public class UsernameDaoAuthenticationProvider extends DaoAuthenticationProvider {
    private SecurityAuthService securityAuthService;

    public void setSecurityAuthService(SecurityAuthService securityAuthService) {
        this.securityAuthService = securityAuthService;
    }

    /**
     * 登录成功后才加载权限
     * order - 1
     *
     * @param principal
     * @param authentication
     * @param user
     * @return
     */
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        BaseUser u = (BaseUser) principal;
        Auth auth = securityAuthService.loadNewAuthByUserId(u.getId());
        return auth;
    }
}
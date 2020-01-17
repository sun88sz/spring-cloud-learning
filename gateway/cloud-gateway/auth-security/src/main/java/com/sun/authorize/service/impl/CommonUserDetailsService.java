package com.sun.authorize.service.impl;

import com.sun.authorize.entity.IUser;
import com.sun.authorize.service.SecurityUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 登录查询用户接口
 *
 * @author Sunchenjie
 * @version 1.0
 */
public class CommonUserDetailsService implements UserDetailsService {

    private SecurityUserService securityUserService;

    public CommonUserDetailsService(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IUser user = securityUserService.selectByUsername(username);
        return user;
    }

}

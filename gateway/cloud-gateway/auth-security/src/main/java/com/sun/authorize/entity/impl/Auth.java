package com.sun.authorize.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.sun.authorize.entity.IAuth;
import com.sun.authorize.entity.IUser;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class Auth extends UsernamePasswordAuthenticationToken implements IAuth {

    private BaseUser user;
    private List<BaseRole> roles;
    private List<BaseResource> resources;

    public Auth() {
        super(null, null, Lists.newArrayList());
    }

    public Auth(IUser user) {
        super(user, user.getPassword(), Lists.newArrayList());
        this.user = (BaseUser) user;
    }

    public Auth(IUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user, user.getPassword(), authorities);
    }

    @Override
    @JsonIgnore
    public Object getPrincipal() {
        return user;
    }

    @Override
    @JsonIgnore
    public Object getCredentials() {
        if (user != null) {
            return user.getPassword();
        }
        return null;
    }

    @Override
    @JsonIgnore
    public Object getDetails() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    @JsonIgnore
    public String getName() {
        IUser user = getUser();
        if (user != null) {
            return user.getName();
        }
        return null;
    }
}

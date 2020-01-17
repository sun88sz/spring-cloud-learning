package com.sun.authorize.entity.impl;

import com.sun.authorize.entity.IDepartment;
import com.sun.authorize.entity.IResource;
import com.sun.authorize.entity.IUser;
import lombok.Data;

import java.util.List;

/**
 * 用户
 *
 * @author SunChenjie
 * @version 1.0
 */
@Data
public class BaseUser implements IUser {

    private Long id;
    private String username;
    private String password;
    private String name;
    private IDepartment department;
    private boolean isEnabled = true;

    /**
     * 权限
     */
    private List<IResource> resources;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean getIsEnabled(boolean enabled) {
        return isEnabled;
    }

    public void setIsEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}

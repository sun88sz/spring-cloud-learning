/*
 * Copyright 2019 http://www.hswebframework.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.sun.authorize.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户信息
 *
 * @author Sunchenjie
 */
public interface IUser extends UserDetails {


    /**
     * @return 用户ID
     */
    Long getId();

    /**
     * @return 姓名
     */
    String getName();

    void setName(String name);

    /**
     * @return 部门
     */
    IDepartment getDepartment();

    void setDepartment(IDepartment department);

    /**
     * 权限
     */
    List<IResource> getResources();

    @JsonIgnore
    @Override
    default Collection<IResource> getAuthorities() {
        return getResources();
    }

    @Override
    String getPassword();

    void setPassword(String password);

    @JsonIgnore
    @Override
    boolean isAccountNonExpired();

    @JsonIgnore
    @Override
    boolean isAccountNonLocked();

    @JsonIgnore
    @Override
    boolean isCredentialsNonExpired();

    @JsonIgnore
    @Override
    boolean isEnabled();
}

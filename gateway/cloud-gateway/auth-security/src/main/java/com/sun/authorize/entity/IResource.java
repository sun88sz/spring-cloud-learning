/*
 * Copyright 2016 http://www.hswebframework.org
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
import org.springframework.security.core.GrantedAuthority;

/**
 * 用户持有的权限信息,包含了权限基本信息
 * 是用户权限的重要接口。
 * <p>
 * id、name、code、sequence、parentId、systemId、description、type、url
 *
 * @author Sunchenjie
 * @version 1.0
 */
public interface IResource extends GrantedAuthority {

    /**
     * id
     * @return
     */
    Long getId();

    /**
     * 全局不可重复，即使systemId一致
     *
     * @return
     */
    String getCode();

    /**
     * 资源类型
     * 菜单/权限/等
     *
     * @return
     */
    String getType();


    /**
     * 覆盖 基类GrantedAuthority的方法
     *
     * @return
     */
    @JsonIgnore
    default String getAuthority() {
        return getCode();
    }

}

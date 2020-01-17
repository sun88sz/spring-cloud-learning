package com.sun.authorize.service;

import com.sun.authorize.entity.IRole;

import java.util.List;

/**
 * 角色
 */
public interface SecurityRoleService {
    
    /**
     * 根据用户id查询角色信息
     *
     * @param userId
     * @return
     */
    List<IRole> selectRoleByUser(Long userId);
}

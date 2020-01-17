package com.sun.authorize.service;

import com.sun.authorize.entity.IResource;

import java.util.List;
import java.util.Map;

/**
 * url-auth 映射
 *
 * @author Sunchenjie
 * @version 1.0
 */
public interface SecurityPermissionService {

    /**
     * 根据用户id查询权限信息
     * 用于登录成功后的加载权限
     *
     * @param userId
     * @return
     */
    List<IResource> selectResourceByUser(Long userId);

    /**
     * @return 所有系统配置的方法url和权限的映射
     */
    Map<String, List<IResource>> selectAllMapping();
}

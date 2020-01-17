package com.sun.authorize.service;


import com.sun.authorize.entity.impl.Auth;

/**
 * 加载auth信息
 *
 * @author SunChenjie
 * @version 1.0
 */
public interface SecurityAuthService {

    /**
     * 清空缓存用户数据
     */
    void removeCacheAllUsers();

    /**
     * 清空缓存用户数据
     *
     * @param userId
     */
    void removeCacheUserById(Long userId);

    /**
     * 根据 userId 查询 用户&权限
     *
     * @param userId
     * @return
     */
    Auth loadAuthByUserId(Long userId);

    /**
     * 从数据库中加载最新的用户信息
     *
     * @param userId
     * @return
     */
    Auth loadNewAuthByUserId(Long userId);

}

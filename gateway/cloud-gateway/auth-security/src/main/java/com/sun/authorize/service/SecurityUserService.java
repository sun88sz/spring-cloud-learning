package com.sun.authorize.service;

import com.sun.authorize.entity.IUser;

/**
 * 自定义 用户接口
 * 如需要使用当前模块，必须实现该接口的方法
 *
 * @author SunChenjie
 * @version 1.0
 */
public interface SecurityUserService {

    /**
     * 用户根据username查询
     * 供登录使用
     *
     * @param username
     * @return
     */
    IUser selectByUsername(String username);


    /**
     * 用户根据 id 查询
     * 供鉴权后使用
     *
     * @param id
     * @return
     */
    IUser selectById(Long id);

}

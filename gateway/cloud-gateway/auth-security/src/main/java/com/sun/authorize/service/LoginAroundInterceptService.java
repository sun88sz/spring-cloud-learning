package com.sun.authorize.service;

/**
 * 登录后处理
 */
public interface LoginAroundInterceptService {

    /**
     *
     */
    void afterLoginSuccessful(Long userId);

    /**
     *
     */
    void afterLoginFailed(String username);
}

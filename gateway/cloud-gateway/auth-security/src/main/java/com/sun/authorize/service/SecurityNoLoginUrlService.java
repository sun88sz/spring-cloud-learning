package com.sun.authorize.service;

import java.util.List;

public interface SecurityNoLoginUrlService {

    /**
     * 无需登录的接口地址
     *
     * @return
     */
    List<String> noLoginUrl();
}

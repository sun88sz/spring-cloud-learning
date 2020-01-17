package com.sun.login.handler;

import cn.lecons.manage.authorization.authorize.AuthenticationHolder;
import cn.lecons.manage.authorization.authorize.entity.impl.Auth;
import cn.lecons.manage.authorization.authorize.service.LoginAroundInterceptService;
import cn.lecons.manage.framework.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理
 *
 * @author SunChenjie
 * @version 1.0
 */
@Slf4j
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    private LoginAroundInterceptService loginAroundInterceptService;

    public void setLoginAroundInterceptService(LoginAroundInterceptService loginAroundInterceptService) {
        this.loginAroundInterceptService = loginAroundInterceptService;
    }

    /**
     * 登录成功后修改返回
     * order - 3
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        // 登录成功后 返回用户信息&权限
        Auth auth = AuthenticationHolder.get();
        Response success = new Response<>(auth);

        // 登录成功后后序处理
        try {
            loginAroundInterceptService.afterLoginSuccessful(auth.getUser().getId());
        } catch (Exception e) {
            log.error("登录成功后后序处理出错", e);
        }

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(success));
    }
}
package com.sun.login.handler;

import cn.lecons.manage.authorization.authorize.service.LoginAroundInterceptService;
import cn.lecons.manage.authorization.exception.AuthorizeExceptionCode;
import cn.lecons.manage.framework.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.lecons.manage.authorization.config.AuthorizationConst.USERNAME;

/**
 * 登录失败处理
 *
 * @author SunChenjie
 * @version 1.0
 */
@Slf4j
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    private LoginAroundInterceptService loginAroundInterceptService;

    public void setLoginAroundInterceptService(LoginAroundInterceptService loginAroundInterceptService) {
        this.loginAroundInterceptService = loginAroundInterceptService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ae) throws IOException {
        // 登录失败后 后序处理
        try {
            Object username = httpServletRequest.getAttribute(USERNAME);
            if (username != null) {
                loginAroundInterceptService.afterLoginFailed((String) username);
            }
        } catch (Exception e) {
            log.error("登录失败后后序处理出错", e);
        }

        Response error = new Response<>();
        error.setError(AuthorizeExceptionCode.LOGIN_DENY);

        // TODO 根据不同错误报错
        if (ae != null) {
            if (ae instanceof DisabledException) {
                error.setError(AuthorizeExceptionCode.LOGIN_USER_DISABLE);
            }
        }

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
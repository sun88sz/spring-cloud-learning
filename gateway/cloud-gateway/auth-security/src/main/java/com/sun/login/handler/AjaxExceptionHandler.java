package com.sun.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.authorize.entity.impl.Response;
import com.sun.exception.AuthorizeExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录返回
 *
 * @author SunChenjie
 * @version 1.0
 */
public class AjaxExceptionHandler extends LoginUrlAuthenticationEntryPoint {

    private ObjectMapper objectMapper = new ObjectMapper();

    public AjaxExceptionHandler() {
        super("/");
    }

    public AjaxExceptionHandler(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * Performs the redirect (or forward) to the login form URL.
     *
     * @param request
     * @param response
     * @param authException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Response error = new Response<>();
        error.setError(AuthorizeExceptionCode.UN_LOGIN);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}

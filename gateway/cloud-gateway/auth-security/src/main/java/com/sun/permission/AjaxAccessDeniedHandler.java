package com.sun.permission;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.authorize.entity.impl.Response;
import com.sun.exception.AuthorizeExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限验证失败处理
 *
 * @author SunChenjie
 * @version 1.0
 */
@Slf4j
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Response error = new Response<>();
        error.setError(AuthorizeExceptionCode.ACCESS_DENY);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
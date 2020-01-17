package com.sun.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.authorize.entity.impl.Response;
import com.sun.exception.AuthorizeExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * session过期策略
 *
 * @author SunChenjie
 * @version 1.0
 */
@Slf4j
public class AjaxSessionExpiredStrategyHandler implements SessionInformationExpiredStrategy {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        Response error = new Response<>();
        error.setError(AuthorizeExceptionCode.LOGIN_DENY);

        HttpServletResponse httpServletResponse = event.getResponse();
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(error));
    }
}

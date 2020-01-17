package com.sun.login.filter;

import cn.lecons.manage.authorization.authorize.entity.impl.BaseUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static cn.lecons.manage.authorization.config.AuthorizationConst.PASSWORD;
import static cn.lecons.manage.authorization.config.AuthorizationConst.USERNAME;

/**
 * 转换json请求登录
 * <p>
 * 该类继承自 UsernamePasswordAuthenticationFilter，重写了其中方法 ,
 * attemptAuthentication：接收并解析用户凭证。
 *
 * @author Sunchenjie
 * @version 1.0
 */
@Slf4j
public class AjaxUsernamePasswordLoginFilter extends UsernamePasswordAuthenticationFilter {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //attempt Authentication when Content-Type is json
        String contentType = request.getContentType();
        if (StringUtils.isNotBlank(contentType)) {
            contentType = contentType.toLowerCase();
            if (MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(contentType) || MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentType)) {
                //use jackson to deserialize json
                UsernamePasswordAuthenticationToken authRequest = null;
                try (InputStream is = request.getInputStream()) {
                    String userStr = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
                    BaseUser user = mapper.readValue(userStr, BaseUser.class);

                    request.setAttribute(USERNAME, user.getUsername());
                    request.setAttribute(PASSWORD, user.getPassword());
                    authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
                } catch (IOException e) {
                    log.info("[Spring Security] 登录json转换错误", e);
                } finally {
                    if (authRequest == null) {
                        authRequest = new UsernamePasswordAuthenticationToken("", "");
                    }
                    setDetails(request, authRequest);
                }
                return this.getAuthenticationManager().authenticate(authRequest);
            }
            //transmit it to UsernamePasswordAuthenticationFilter
            else {
                return super.attemptAuthentication(request, response);
            }
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
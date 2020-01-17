package com.sun.login.filter;

import cn.lecons.manage.authorization.authorize.entity.impl.BaseUser;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * <p>
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中方法 ,
 * successfulAuthentication：用户成功登录后，这个方法会被调用，我们在这个方法里生成token并返回。
 *
 * @author Sunchenjie
 * @version 1.0
 */
public class JWTLoginFilter extends AjaxUsernamePasswordLoginFilter {

    private JWTTokenService jwtTokenService;

    public JWTLoginFilter(JWTTokenService jwtTokenService) {
        super();
        this.jwtTokenService = jwtTokenService;
    }

    /**
     * 登录成功后，token返回
     * order - 2
     *
     * @param request
     * @param response
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        BaseUser user = (BaseUser) auth.getPrincipal();
        jwtTokenService.setResponseToken(auth.getName(), user.getId(), response);
        user.setPassword("------");
        // 设置缓存
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Fire event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(auth, this.getClass()));
        }

        getSuccessHandler().onAuthenticationSuccess(request, response, auth);
    }

}

package com.sun.login.filter;

import cn.lecons.manage.authorization.authorize.entity.impl.Auth;
import cn.lecons.manage.authorization.authorize.service.SecurityAuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter
 * 在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 *
 * @author Sunchenjie
 * @version 1.0
 */
@Slf4j
public class JWTAuthorizeCheckFilter extends OncePerRequestFilter {

    private SecurityAuthService securityAuthService;

    private JWTTokenService jwtTokenService;

    public JWTAuthorizeCheckFilter(SecurityAuthService securityAuthService, JWTTokenService jwtTokenService) {
        super();
        this.securityAuthService = securityAuthService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 验证权限
        String token = jwtTokenService.getTokenFromRequest(request);
        if (StringUtils.isNotBlank(token)) {
            // parse the token.
            // 解码的过程可能抛出过期异常
            try {
                Claims claims = jwtTokenService.parseToken(token);
                if (claims != null) {
                    Long userId = jwtTokenService.getUserIdByClaims(claims);
                    if (userId != null && userId != 0) {
                        // 加载用户
                        Auth auth = securityAuthService.loadAuthByUserId(userId);
                        // 用户缓存
                        SecurityContextHolder.getContext().setAuthentication(auth);

                        // 在token快失效的时候 需要提前刷新token
                        boolean expire = jwtTokenService.checkExpireTimeByClaims(claims);
                        if (expire) {
                            jwtTokenService.setResponseToken(auth.getName(), userId, response);
                        }
                    }

                }
            } catch (ExpiredJwtException e) {
                log.info("User过期", e);
//                throw new BusinessException(AuthorizeExceptionCode.UN_LOGIN);
            } catch (Exception e) {
                log.info("JWT错误", e);
//                throw new BusinessException(AuthorizeExceptionCode.UN_LOGIN);
            }
        }
        chain.doFilter(request, response);
    }

}

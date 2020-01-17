package com.sun.login.filter;

import cn.lecons.manage.authorization.config.AuthorizationConst;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;

/**
 * jwt token 工具类
 */
public class JWTTokenService {

    // jwt 过期时间
    private long jwtExpire;
    // token name
    private String tokenName;
    // token加密秘钥
    private Key key;

    public JWTTokenService(long jwtExpire, Key key, String tokenName) {
        super();
        this.jwtExpire = jwtExpire;
        this.tokenName = tokenName;
        // jwt key
        this.key = key;
    }


    /**
     * 设置新的token
     *
     * @param subjectName
     * @param userId
     * @param response
     */
    public void setResponseToken(String subjectName, Long userId, HttpServletResponse response) {
        Claims claims = Jwts.claims();
        claims.put(AuthorizationConst.USER_ID, userId);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subjectName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpire * 1000))
                .signWith(key)
                .compact();
        response.setCharacterEncoding("UTF-8");
        response.setHeader(tokenName, token);
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(tokenName);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(tokenName);
        }
        return token;
    }

    /**
     * 解析token，返回用户id
     *
     * @param token
     * @return 用户id
     */
    public Claims parseToken(String token) {
        Claims body = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return body;
    }

    /**
     * @param body
     * @return
     */
    public Long getUserIdByClaims(Claims body) {
        if (body != null) {
            Object oUserId = body.get(AuthorizationConst.USER_ID);
            if (oUserId != null && NumberUtils.isNumber(oUserId.toString())) {
                long userId = Long.parseLong(oUserId.toString());
                return userId;
            }
        }
        return null;
    }

    /**
     * 是否该更新token了
     *
     * @param body
     * @return
     */
    public boolean checkExpireTimeByClaims(Claims body) {
        if (body != null) {
            Date expiration = body.getExpiration();
            if (expiration != null) {
                // 除以 1000 * 2，即有效期过了一半就刷新token
                if ((expiration.getTime() - System.currentTimeMillis()) < (jwtExpire * 500)) {
                    return true;
                }
            }
        }
        return false;
    }
}

package com.sun.exception;

import com.sun.authorize.entity.impl.ErrorCode;

/**
 * 鉴权错误代码
 *
 * @author Sunchenjie
 * @version 1.0
 */
public interface AuthorizeExceptionCode {

    ErrorCode LOGIN_DENY = new ErrorCode(401, "用户名或密码错误");
    ErrorCode LOGIN_USER_DISABLE = new ErrorCode(401, "用户账号已停用");

    ErrorCode UN_LOGIN = new ErrorCode(402, "用户未登录或已过期，请重新登录");
    ErrorCode ACCESS_DENY = new ErrorCode(403, "用户无权限访问接口");

}
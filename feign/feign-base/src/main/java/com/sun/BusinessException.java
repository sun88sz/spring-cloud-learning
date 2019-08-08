package com.sun;

import lombok.Getter;

public class BusinessException extends RuntimeException {

    /**
     * 错误代码
     */
    @Getter
    private String code;

    /**
     * 错误信息
     */
    @Getter
    private String message;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        this.message = message;
    }

}

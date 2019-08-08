package com.sun;

/**
 * feign调用异常
 */
public class FeignCallException extends BusinessException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     *
     * @param code
     * @param message
     */
    public FeignCallException(String code, String message) {
        super(code, message);
    }

    public FeignCallException(String message) {
        super(message);
    }
}

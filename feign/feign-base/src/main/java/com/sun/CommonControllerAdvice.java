package com.sun;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * 异常统一处理
 *
 * @author Sun
 */
@Slf4j
@ControllerAdvice("com.sun")
public class CommonControllerAdvice {

    /**
     * 系统自定义异常
     *RequestInterceptor
     * @param exception
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseResult handleBusinessException(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseResult.error(exception.getCode(), exception.getMessage());
    }

    /**
     * Validation 参数验证错误
     *
     * @param bindException
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseResult handleValidatorException(MethodArgumentNotValidException bindException) {
        List<FieldError> errors = bindException.getBindingResult().getFieldErrors();
        return buildException(errors);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseResult handleValidatorException(BindException bindException) {
        List<FieldError> errors = bindException.getBindingResult().getFieldErrors();
        return buildException(errors);
    }

    private ResponseResult buildException(List<FieldError> errors) {
        StringBuilder message = new StringBuilder();
        for (int i = 0, size = errors.size(); i < size; i++) {
            FieldError fieldError = errors.get(i);
            message.append(fieldError.getDefaultMessage());
            if (i < (size - 1)) {
                message.append("\r\n");
            }
        }
        log.error("参数验证错误", message.toString());

        return ResponseResult.error("100000000001", message.toString());
    }


    /**
     * 异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseResult handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseResult.error(ExceptionUtils.getStackTrace(exception));
    }

}

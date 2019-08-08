package com.sun;

import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 响应消息。controller中处理后，返回此对象，响应请求结果给客户端。
 * <p>
 * 只要正常返回（即使 ResponseResult.error ）那么 http请求 的 status=200
 * 但 ResponseResult.error 的返回中 status 不同
 *
 * @author Sun
 */
@Data
public class ResponseResult<T> implements Serializable {

    private String code;

    protected String message;

    protected T result;

    protected Integer status;

    private Long timestamp;

    /**
     * 过滤字段：指定需要序列化的字段
     */
    private transient Map<Class<?>, Set<String>> includes;

    /**
     * 过滤字段：指定不需要序列化的字段
     */
    private transient Map<Class<?>, Set<String>> excludes;

    /**
     * 私有构造
     * 禁止new
     */
    private ResponseResult() {

    }

    /**
     * 错误返回
     *
     * @param
     * @return
     */
    public static <T> ResponseResult<T> error(BusinessException exception) {
        return error(ResultStatus.ERROR.getStatus(), exception.getCode(), exception.getMessage());
    }

    public static <T> ResponseResult<T> error(String message) {
        return error(ResultStatus.ERROR.getStatus(), null, message);
    }

    public static <T> ResponseResult<T> error(String code, String message) {
        return error(ResultStatus.ERROR.getStatus(), code, message);
    }

    private static <T> ResponseResult<T> error(int status, String code, String message) {
        return new ResponseResult<T>().message(message).status(status).code(code).putTimeStamp();
    }

    /**
     * 正确返回
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> ok() {
        return ok(null);
    }

    public static <T> ResponseResult<T> ok(T result) {
        return ok(result, null);
    }

    private static <T> ResponseResult<T> ok(T result, String message) {
        return new ResponseResult<T>().result(result).message(message).status(ResultStatus.OK.getStatus()).putTimeStamp();
    }


    public ResponseResult<T> include(Class<?> type, String... fields) {
        return include(type, Arrays.asList(fields));
    }

    public ResponseResult<T> include(Class<?> type, Collection<String> fields) {
        if (includes == null)
            includes = new HashMap<>();
        if (fields == null || fields.isEmpty()) return this;
        fields.forEach(field -> {
            if (field.contains(".")) {
                String tmp[] = field.split("[.]", 2);
                try {
                    Field field1 = type.getDeclaredField(tmp[0]);
                    if (field1 != null) {
                        include(field1.getType(), tmp[1]);
                    }
                } catch (Throwable e) {
                }
            } else {
                getStringListFromMap(includes, type).add(field);
            }
        });
        return this;
    }

    public ResponseResult<T> exclude(Class type, Collection<String> fields) {
        if (excludes == null)
            excludes = new HashMap<>();
        if (fields == null || fields.isEmpty()) return this;
        fields.forEach(field -> {
            if (field.contains(".")) {
                String tmp[] = field.split("[.]", 2);
                try {
                    Field field1 = type.getDeclaredField(tmp[0]);
                    if (field1 != null) {
                        exclude(field1.getType(), tmp[1]);
                    }
                } catch (Throwable e) {
                }
            } else {
                getStringListFromMap(excludes, type).add(field);
            }
        });
        return this;
    }

    public ResponseResult<T> exclude(Collection<String> fields) {
        if (excludes == null)
            excludes = new HashMap<>();
        if (fields == null || fields.isEmpty()) return this;
        Class type;
        if (getResult() != null) type = getResult().getClass();
        else return this;
        exclude(type, fields);
        return this;
    }

    public ResponseResult<T> include(Collection<String> fields) {
        if (includes == null)
            includes = new HashMap<>();
        if (fields == null || fields.isEmpty()) return this;
        Class type;
        if (getResult() != null) type = getResult().getClass();
        else return this;
        include(type, fields);
        return this;
    }

    public ResponseResult<T> exclude(Class type, String... fields) {
        return exclude(type, Arrays.asList(fields));
    }

    public ResponseResult<T> exclude(String... fields) {
        return exclude(Arrays.asList(fields));
    }

    public ResponseResult<T> include(String... fields) {
        return include(Arrays.asList(fields));
    }

    protected Set<String> getStringListFromMap(Map<Class<?>, Set<String>> map, Class type) {
        return map.computeIfAbsent(type, k -> new HashSet<>());
    }

    public ResponseResult<T> status(int status) {
        this.status = status;
        return this;
    }

    public ResponseResult<T> code(String code) {
        this.code = code;
        return this;
    }

    private ResponseResult<T> message(String message) {
        this.message = message;
        return this;
    }

    private ResponseResult<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ResponseResult<T> result(T result) {
        this.result = result;
        return this;
    }


}
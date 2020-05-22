package com.oem.nms.common.entity.response;

import lombok.Getter;

/**
 * @author Seaway John
 */
@Getter
public enum ResponseCode {

    /**
     * 成功
     */
    OK("00000", "success"),

    /**
     * 用户端错误
     */
    ERROR_USER("A0001", "user error"),

    /**
     * 用户注册错误
     */
    ERROR_USER_REGISTER("A0100", "user register error"),

    /**
     * 用户身份校验失败
     */
    ERROR_USER_UNAUTHORIZED("A0220", "user unauthorized error"),

    /**
     * 访问未授权
     */
    ERROR_USER_PERMISSION_DENIED_TOKEN("A0301 ", "user permission denied error"),

    /**
     * 授权已过期
     */
    ERROR_USER_EXPIRED_TOKEN("A0311", "user expired token error"),

    /**
     * 用户签名异常
     */
    ERROR_USER_INVALID_TOKEN("A0340", "user invalid token error"),

    /**
     * 用户重复请求
     */
    ERROR_USER_REPEAT_REQUEST("A0506", "user repeat request error"),

    /**
     * 系统执行出错
     */
    ERROR_SERVER_ERROR("B0001", "server error");

    private String code;

    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}

package com.oem.nms.common.util;

/**
 * @author Seaway John
 */
public class Constants {

    /**
     * JWT
     */
    public static final String JWT_SIGNATURE_KEY = "oem-nms-key";
    public static final String JWT_AUTH_HEADER_KEY = "Authorization";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_PARAM = "token";

    /**
     * Header
     */
    public static final String HEADER_IP = "x-ip";
    public static final String HEADER_USERNAME = "x-username";
    public static final String HEADER_AUTHORITIES = "x-authorities";


    private Constants() {
    }

}

package com.oem.nms.gateway.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Seaway John
 */
public class UriUtil {

    private UriUtil() {
    }

    public static String getIp(HttpRequest request) {
        HttpHeaders header = request.getHeaders();
        List<String> params = Arrays.asList("x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP");

        for (String param : params) {
            String ip = header.getFirst(param);
            if (validIp().test(ip)) {
                return ip;
            }
        }

        return null;
    }

    private static Predicate<String> validIp() {
        return ip -> StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip);
    }

}

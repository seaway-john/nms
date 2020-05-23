package com.oem.nms.gateway.interceptor;

import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.entity.response.ResponseCode;
import com.oem.nms.common.util.Constants;
import com.oem.nms.common.util.JsonUtil;
import com.oem.nms.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Seaway John
 * 这个filter拦截gateway转发到其它微服务，自身的比如/actuator/health不拦截
 */
@Slf4j
@Configuration
public class HttpFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        HttpHeaders header = request.getHeaders();
        HttpMethod method = request.getMethod();
        String url = request.getURI().getPath();

        if (ignoreUrl(method, url)) {
            return chain.filter(exchange);
        }

        String token = request.getQueryParams().getFirst(Constants.JWT_TOKEN_PARAM);
        if (StringUtils.isEmpty(token)) {
            String authorization = header.getFirst(Constants.JWT_AUTH_HEADER_KEY);
            if (StringUtils.isEmpty(authorization) || !authorization.startsWith(Constants.JWT_TOKEN_PREFIX)) {
                return error(exchange, ResponseCode.ERROR_USER_UNAUTHORIZED);
            }

            token = authorization.substring(Constants.JWT_TOKEN_PREFIX.length());
        }

        Response<Claims> claimsResponse = JwtUtil.parse(token);
        if (claimsResponse.isError()) {
            return error(exchange, claimsResponse.getResponseCode());
        }

        Claims claims = claimsResponse.getData();

        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header(Constants.HEADER_IP, getIp().apply(request));
        mutate.header(Constants.HEADER_USERNAME, String.valueOf(claims.get("user_name")));
        mutate.header(Constants.HEADER_AUTHORITIES, JsonUtil.toJson(claims.get("authorities")));

        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    private boolean ignoreUrl(HttpMethod method, String url) {
        Map<String, HttpMethod> map = new HashMap<>(8);
        map.put("/v2/api-docs", HttpMethod.GET);
        map.put("/oauth/token", HttpMethod.POST);

        if (!map.containsKey(url)) {
            return false;
        }

        return method.equals(map.get(url));
    }

    private Mono<Void> error(ServerWebExchange exchange, ResponseCode responseCode) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        switch (responseCode) {
            case ERROR_USER_UNAUTHORIZED:
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case ERROR_USER_PERMISSION_DENIED_TOKEN:
                httpStatus = HttpStatus.FORBIDDEN;
                break;
            case ERROR_USER_REPEAT_REQUEST:
                httpStatus = HttpStatus.CONFLICT;
                break;
            default:
                break;
        }

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        String returnStr = JsonUtil.toJson(new Response<>(responseCode));

        DataBuffer buffer = response.bufferFactory().wrap(returnStr.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Flux.just(buffer));
    }

    private Function<ServerHttpRequest, String> getIp() {
        return request -> {
            HttpHeaders header = request.getHeaders();
            List<String> params = Arrays.asList("x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP");

            for (String param : params) {
                String ip = header.getFirst(param);
                if (validIp().test(ip)) {
                    return ip;
                }
            }

            if (request.getRemoteAddress() == null) {
                return null;
            }

            return request.getRemoteAddress().getHostName();
        };
    }

    private Predicate<String> validIp() {
        return ip -> org.apache.commons.lang.StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip);
    }

}

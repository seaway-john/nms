package com.oem.nms.common.swagger.config;

import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.entity.response.ResponseCode;
import com.oem.nms.common.exception.ConflictException;
import com.oem.nms.common.exception.ForbiddenException;
import com.oem.nms.common.exception.UnauthorizedException;
import com.oem.nms.common.swagger.entity.RequestInfo;
import com.oem.nms.common.swagger.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Seaway John
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(WebClientResponseException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<String> handleBadRequestExceptionException(HttpServletRequest request, WebClientResponseException.BadRequest e) {
        return handle(request, e, ResponseCode.ERROR_USER);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Response<String> handleUnAuthorizedException(HttpServletRequest request, UnauthorizedException e) {
        return handle(request, e, ResponseCode.ERROR_USER_INVALID_TOKEN);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response<String> handleForbiddenException(HttpServletRequest request, ForbiddenException e) {
        return handle(request, e, ResponseCode.ERROR_USER_PERMISSION_DENIED_TOKEN);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response<String> handleRepeatSubmitException(HttpServletRequest request, ConflictException e) {
        return handle(request, e, ResponseCode.ERROR_USER_REPEAT_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> handleException(HttpServletRequest request, Exception e) {
        return handle(request, e, ResponseCode.ERROR_SERVER_ERROR);
    }

    private Response<String> handle(HttpServletRequest request, Exception e, ResponseCode responseCode) {
        RequestInfo requestInfo = HttpUtil.convert(request);

        log.warn("Handle {}, ip {}, username {}, reason {}", e.getClass().getSimpleName(), requestInfo.getIp(), requestInfo.getUsername(), e.getMessage());

        return new Response<>(responseCode, e.getMessage());
    }

}

package com.oem.nms.common.swagger.config;

import com.oem.nms.common.entity.db.admin.RoleType;
import com.oem.nms.common.exception.ForbiddenException;
import com.oem.nms.common.swagger.entity.RequestInfo;
import com.oem.nms.common.swagger.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Seaway John
 */
@Slf4j
@Aspect
@Component
public class RoleAop {

    @Pointcut("@annotation(com.oem.nms.common.swagger.config.HasRole)")
    private void hasRoleAop() {
    }

    @Around("hasRoleAop()")
    public Object preHasRole(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();

        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        if (!targetMethod.isAnnotationPresent(HasRole.class)) {
            throw new ForbiddenException("invalid annotation");
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestInfo requestInfo = HttpUtil.convert(request);

        RoleType role = targetMethod.getAnnotation(HasRole.class).role();
        if (!requestInfo.getRoles().contains(role)) {
            throw new ForbiddenException("permission denied");
        }

        log.info("preHasRole, ip {}, username {}, role {}, className {}, methodName {}", requestInfo.getIp(), requestInfo.getUsername(), role, className, methodName);

        return point.proceed();
    }

}

package com.oem.nms.common.swagger.config;

import com.oem.nms.common.entity.request.RequestInfo;
import com.oem.nms.common.exception.ForbiddenException;
import com.oem.nms.common.mq.entity.LogMessageLevel;
import com.oem.nms.common.mq.service.MqLogSender;
import com.oem.nms.common.swagger.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.lang.reflect.Method;

/**
 * @author Seaway John
 */
@Slf4j
@Aspect
@Component
public class RoleAop {

    private final MqLogSender mqLogSender;

    @Autowired
    public RoleAop(MqLogSender mqLogSender) {
        this.mqLogSender = mqLogSender;
    }

    @Pointcut("@annotation(com.oem.nms.common.swagger.config.HasRole)")
    private void hasRoleAop() {
    }

    @Around("hasRoleAop()")
    public Object preHasRole(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();

        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        if (!targetMethod.isAnnotationPresent(HasRole.class)) {
            throw new ForbiddenException("invalid annotation");
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestInfo requestInfo = HttpUtil.convert(request);

        HasRole hasRole = targetMethod.getAnnotation(HasRole.class);
        if (!requestInfo.getRoles().contains(hasRole.role())) {
            throw new ForbiddenException("permission denied");
        }

        if (hasRole.log()) {
            String className = point.getTarget().getClass().getSimpleName();
            String methodName = signature.getName();
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String query = request.getQueryString();

            StringBuilder sb = new StringBuilder();
            try (BufferedReader br = request.getReader()) {
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception ignore) {
            }

            String message = String.format("%s %s in %s#%s, query %s, body %s", method, uri, className, methodName, query, sb.toString());
            mqLogSender.send(requestInfo, LogMessageLevel.INFO, message);
        }

        return point.proceed();
    }

}

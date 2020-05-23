package com.oem.nms.common.swagger.config;

import com.oem.nms.common.entity.db.admin.RoleType;

import java.lang.annotation.*;

/**
 * @author Seaway John
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasRole {

    RoleType role();

    boolean log() default true;

}

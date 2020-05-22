package com.oem.nms.common.swagger.entity;

import com.oem.nms.common.entity.db.admin.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Seaway John
 */
@Getter
@Setter
public class RequestInfo {

    private String ip;

    private String username;

    private String authorities;

    private Set<RoleType> roles;

    public RequestInfo() {
        this.roles = new HashSet<>();
    }

}

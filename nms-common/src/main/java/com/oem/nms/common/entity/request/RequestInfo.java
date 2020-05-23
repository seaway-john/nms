package com.oem.nms.common.entity.request;

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

    private Set<RoleType> roles;

    public RequestInfo() {
        this.roles = new HashSet<>();
    }

}

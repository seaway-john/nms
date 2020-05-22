package com.oem.nms.admin.entity.db.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oem.nms.common.entity.db.admin.RoleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @author Seaway John
 */
@Getter
@Setter
public class User implements UserDetails {

    private String username;

    @JsonIgnore
    private String password;

    private RoleType role;

    @JsonIgnore
    private List<Authority> authorities;

    @JsonIgnore
    private boolean accountNonExpired;

    @JsonIgnore
    private boolean accountNonLocked;

    @JsonIgnore
    private boolean credentialsNonExpired;

    private boolean enabled;

    public User() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

}

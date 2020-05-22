package com.oem.nms.admin.service;

import com.oem.nms.admin.entity.db.admin.Authority;
import com.oem.nms.admin.entity.db.admin.User;
import com.oem.nms.common.entity.db.admin.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seaway John
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("Username not found");
        }

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("123456"));

        user.setRole(RoleType.ROLE_ADMIN);

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority(String.valueOf(RoleType.ROLE_ADMIN)));
        authorities.add(new Authority(String.valueOf(RoleType.ROLE_USER)));
        authorities.add(new Authority(String.valueOf(RoleType.ROLE_GUEST)));

        user.setAuthorities(authorities);

        return user;
    }

}

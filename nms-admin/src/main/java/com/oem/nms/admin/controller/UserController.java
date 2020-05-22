package com.oem.nms.admin.controller;

import com.oem.nms.common.entity.db.admin.RoleType;
import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.swagger.config.HasRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Seaway John
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/test")
    public int test() {
        return 0;
    }

    @HasRole(role = RoleType.ROLE_ADMIN)
    @GetMapping("/id/{id}")
    public Response<Map<String, String>> getById(@PathVariable String id) {
        log.info("getById id {}", id);

        return new Response<>(new HashMap<>(8));
    }

}

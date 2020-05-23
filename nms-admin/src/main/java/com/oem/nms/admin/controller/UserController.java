package com.oem.nms.admin.controller;

import com.oem.nms.common.entity.db.admin.RoleType;
import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.swagger.config.HasRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @HasRole(role = RoleType.ROLE_ADMIN, log = false)
    @GetMapping("/id/{id}")
    public Response<Map<String, String>> getById(@PathVariable String id) {
        log.info("getById id {}", id);

        return new Response<>(new HashMap<>(8));
    }

    @HasRole(role = RoleType.ROLE_ADMIN)
    @PostMapping
    public Response<String> add(@RequestBody Map<String, String> user) {
        return new Response<>("success");
    }

    @HasRole(role = RoleType.ROLE_ADMIN)
    @PatchMapping("/id/{id}")
    public Response<String> updateById(@PathVariable String id,
                                       @RequestBody Map<String, String> user) {
        return new Response<>("success");
    }

    @HasRole(role = RoleType.ROLE_ADMIN)
    @DeleteMapping("/id/{id}")
    public Response<String> deleteById(@PathVariable String id) {
        return new Response<>("success");
    }

    @HasRole(role = RoleType.ROLE_ADMIN)
    @PatchMapping("/enable/id/{id}")
    public Response<String> triggerById(@PathVariable String id,
                                        @RequestParam(required = false, defaultValue = "true") boolean enable) {
        return new Response<>("success");
    }

}

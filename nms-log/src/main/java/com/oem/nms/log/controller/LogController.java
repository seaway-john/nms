package com.oem.nms.log.controller;

import com.oem.nms.common.entity.db.admin.RoleType;
import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.swagger.config.HasRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Seaway John
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    public LogController() {
    }

    @HasRole(role = RoleType.ROLE_USER, log = false)
    @GetMapping("/list")
    public Response<List<String>> list() {
        log.info("list");

        return new Response<>(new ArrayList<>());
    }


}

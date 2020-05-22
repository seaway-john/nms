package com.oem.nms.manager.controller;

import com.oem.nms.common.entity.db.admin.RoleType;
import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.mq.entity.WebsocketMessage;
import com.oem.nms.common.mq.service.MqClient;
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
@RequestMapping("/topo")
public class TopoController {

    private final MqClient mqClient;

    @Autowired
    public TopoController(MqClient mqClient) {
        this.mqClient = mqClient;
    }

    @GetMapping("/test")
    public int test() {
        WebsocketMessage websocketMessage = new WebsocketMessage();
        websocketMessage.setMessage("TopoController test");

        mqClient.sendWebsocket(websocketMessage);

        return 0;
    }

    @HasRole(role = RoleType.ROLE_USER)
    @GetMapping("/list")
    public Response<List<String>> list() {
        log.info("list");

        return new Response<>(new ArrayList<>());
    }


}

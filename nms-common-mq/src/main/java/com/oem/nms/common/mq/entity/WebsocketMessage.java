package com.oem.nms.common.mq.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Seaway John
 */
@Getter
@Setter
public class WebsocketMessage implements Serializable {

    private String id;

    private String message;

    private LocalDateTime createdTime;

    public WebsocketMessage() {
        this.id = UUID.randomUUID().toString();
        this.createdTime = LocalDateTime.now();
    }

}

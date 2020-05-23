package com.oem.nms.common.mq.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Seaway John
 */
@Getter
@Setter
@ToString
public class LogMessage implements Serializable {

    private String id;

    private String ip;

    private String username;

    private LogMessageLevel level;

    private String message;

    private LocalDateTime createdTime;

    public LogMessage() {
        this.id = UUID.randomUUID().toString();
        this.createdTime = LocalDateTime.now();
    }

}

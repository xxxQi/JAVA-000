package com.mq.core.skeleton;

import lombok.Data;

/**
 * @author: Administrator
 * @description:
 */
@Data
public class Message {
    private String topic;

    private String content;
    public Message(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }
}

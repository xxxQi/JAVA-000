package com.mq.core.controller;

import com.mq.core.skeleton.Message;
import com.mq.core.skeleton.Broker;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基于Spring web（HTTP）的API接口
 *
 * @author lw1243925457
 */
@RestController
public class MQController {
    private final Broker broker;

    public MQController(Broker broker) {
        this.broker = broker;
    }

    @PostMapping("/send")
    public boolean send(@RequestBody Message message) {
        return broker.send(message.getTopic(), message.getContent());
    }

    @GetMapping("/poll")
    public List poll(@RequestParam(value = "topic")String topic,
                     @RequestParam(value = "group")String group) {
        return broker.poll(topic,group);
    }
}

package com.mq.core.skeleton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@Slf4j
public class Broker {
    Map<String, Queue> queueMap = new HashMap<>();

    public boolean send(String topic, String content) {
        log.info("receive message : " + content + " from topic : " + topic);
        Queue queue = queueMap.getOrDefault(topic, new Queue());
        queue.put(content);
        queueMap.put(topic, queue);
        return true;
    }

    public List<String> poll(String topic, String group) {
        log.info("poll data to : " + topic);

        Queue queue = queueMap.get(topic);

        List<String> messages = new ArrayList<>();
        if (queue == null) {
            return messages;
        }

        log.info("queue message amount : " + queue.size());
        while (!queue.isEmpty() ) {
            String message = queue.get(group);
            if (message == null) {
                break;
            }
            messages.add(message);
        }

        return messages;
    }
}

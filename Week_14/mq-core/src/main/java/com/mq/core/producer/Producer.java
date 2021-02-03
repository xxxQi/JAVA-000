package com.mq.core.producer;

import com.mq.core.skeleton.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
public class Producer {
    private final RestTemplate restTemplate = new RestTemplate();
    private Map<String, Object> properties;

    public void setProperties(Map<String, Object> properties){
        this.properties = properties;
    }

    public void send(String topic,String message){
        String brokerUrl = properties.get("url").toString() + "/send";
        HttpEntity<Message> request = new HttpEntity<>(new Message(topic, message));
        ResponseEntity<Boolean> response = restTemplate.postForEntity(brokerUrl, request, Boolean.class);
        log.info("response:[{}]",response);


    }
}

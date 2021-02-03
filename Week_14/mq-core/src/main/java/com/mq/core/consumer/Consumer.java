package com.mq.core.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@Slf4j
public class Consumer {
    private final RestTemplate restTemplate = new RestTemplate();
    private Map<String, Object> properties;
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List poll(){
        String brokerUrl = properties.get("url").toString() + "/poll?topic=" + properties.get("topic") + "&group=" + properties.get("group");
        ResponseEntity<List> response = restTemplate.getForEntity(brokerUrl, List.class);
        return response.getBody();
    }
}

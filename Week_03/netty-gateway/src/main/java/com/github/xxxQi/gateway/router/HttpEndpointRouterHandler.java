package com.github.xxxQi.gateway.router;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Administrator
 * @description:
 */
public class HttpEndpointRouterHandler implements HttpEndpointRouter{
    private final List<String> routes = Arrays.asList("http://localhost:8088");

    //根据权重，返回最优的route
    private String fetchRoute() {
        return routes.get(0);
    }

    @Override
    public String route() {
        return fetchRoute();
    }
}

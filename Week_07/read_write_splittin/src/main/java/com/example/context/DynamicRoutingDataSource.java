package com.example.context;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DynamicRoutingDataSource {
    private final static ThreadLocal<String> HOLDER = new ThreadLocal<>();
    private final static Set<String> DATA_SOURCE_KEYS = new HashSet<>();
    private static String masterRouteKey = "master";
    private static final List<String> slaveDataSourceRouteKeys = new ArrayList<>();

    public static void addMasterDataSourceRouterKey(String master) {
        DynamicRoutingDataSource.masterRouteKey = master;
        DATA_SOURCE_KEYS.add(master);
    }

    public static void addSlaveDataSource(String dataSourceName) {
        DynamicRoutingDataSource.slaveDataSourceRouteKeys.add(dataSourceName);
        DATA_SOURCE_KEYS.add(dataSourceName);
    }


    public void setHolderRouteKey(String routeKey) {
        if (DATA_SOURCE_KEYS.contains(routeKey)) {
            HOLDER.set(routeKey);
        } else {
            throw new IllegalArgumentException(String.format("数据源不存在:%s", routeKey));
        }
    }

    public static String getHolderRouteKey() {
        return HOLDER.get();
    }

    public void removeHolderRouteKey() {
        HOLDER.remove();
    }

    public String getMasterRouteKey(){
        return DynamicRoutingDataSource.masterRouteKey;
    }

    public String getRouteKey(String routeKey) {
        return DATA_SOURCE_KEYS
                .stream()
                .filter(dataSource -> dataSource.equals(routeKey))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("数据源不存在:%s", routeKey)));
    }


    public String loadBalanceOnSlaveDataSource() {
        return slaveDataSourceRouteKeys.get(new Random().nextInt(slaveDataSourceRouteKeys.size()));
    }

}
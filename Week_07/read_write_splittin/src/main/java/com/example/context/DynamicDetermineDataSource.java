package com.example.context;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Objects;

@Slf4j
public class DynamicDetermineDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DynamicRoutingDataSource.getHolderRouteKey();
        log.info("使用数据源：{}", Objects.isNull(dataSourceName) ? "默认数据源":dataSourceName);
        return dataSourceName;
    }
}
package com.example.context;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment evn;
    Binder binder;

    /**
     * 注册多数据源
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //默认数据源
        DataSource defaultDataSource = initializationDefaultDataSource();
        //自定义数据源
        Map<String, DataSource> targetDataSource = initializationTargetDataSource();
        //注册数据源
        registerDataSource(registry, defaultDataSource, targetDataSource);
    }

    private void registerDataSource(BeanDefinitionRegistry registry,
                                    DataSource defaultDataSource,
                                    Map<String, DataSource> targetDataSource) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDetermineDataSource.class);
        //BeanDefinition 添加属性 默认数据源 和 自定义数据源
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.add("defaultTargetDataSource", defaultDataSource);
        propertyValues.add("targetDataSources", targetDataSource);
        //注册BeanDefinition
        registry.registerBeanDefinition("multiDataSource", beanDefinition);
    }

    private Map<String, DataSource> initializationTargetDataSource() {
        Map<String, DataSource> customDataSources = new HashMap<>();
        Class<? extends DataSource> type;
        List<Map> config = binder.bind("spring.datasource.slave",
                Bindable.listOf(Map.class)).get();
        for (int i = 0; i < config.size(); i++) {
            Map<String,Object> customDataSourceProperties = config.get(i);
            type = getDataSourceType((String) customDataSourceProperties.get("type"));
            DataSource customDataSource = bind(type, customDataSourceProperties);
            String dataSourceName = (String) customDataSourceProperties.get("key");
            DynamicRoutingDataSource.addSlaveDataSource(dataSourceName);
            customDataSources.put(dataSourceName, customDataSource);
        }
        return customDataSources;
    }

    private DataSource initializationDefaultDataSource() {
        Map<String, Object> defaultDataSourceProperties = binder.bind("spring.datasource.master",
                Bindable.mapOf(String.class, Object.class)).get();
        String driverType = (String) defaultDataSourceProperties.get("spring.datasource.master.type");
        //获取驱动，绑定属性
        DataSource defaultDataSource = bind(getDataSourceType(driverType), defaultDataSourceProperties);
        DynamicRoutingDataSource.addMasterDataSourceRouterKey("master");
        return defaultDataSource;
    }

    /**
     * 通过字符串获取数据源类型
     */
    private Class<? extends DataSource> getDataSourceType(String className) {
        Class<? extends DataSource> type = null;
        if (StringUtils.hasText(className)) {
            try {
                type = (Class<? extends DataSource>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (type == null) {
            type = HikariDataSource.class;
        }
        return type;
    }

    /**
     * 通过属性和类名称，生成对象
     */
    private <T extends DataSource> T bind(Class<T> tClass, Map properties) {
        ConfigurationPropertySource propertySource = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(propertySource);
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(tClass)).get();
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.evn = environment;
        this.binder = Binder.get(environment);
    }
}
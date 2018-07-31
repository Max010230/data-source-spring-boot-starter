package me.wuxingxing.multi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xingxing.wu
 * @date 2018/7/17
 */
@ConfigurationProperties(prefix = "me.wuxingxing.dynamic")
public class DynamicDataSourceProperties {

    Map<String, DataSourceProperties> datasource = new HashMap<>();

    String mapperLocations;

    String configLocation;

    public Map<String, DataSourceProperties> getDatasource() {
        return this.datasource;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }
}

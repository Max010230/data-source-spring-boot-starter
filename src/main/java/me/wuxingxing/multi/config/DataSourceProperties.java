package me.wuxingxing.multi.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xingxing.wu
 * @date 2018/7/31
 */
@ConfigurationProperties(prefix = "so.sao.dynamic.datasource.*")
public class DataSourceProperties extends HikariConfig {

}

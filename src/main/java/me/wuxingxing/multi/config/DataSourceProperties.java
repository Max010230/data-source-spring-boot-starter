package me.wuxingxing.multi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xingxing.wu
 * @date 2018/7/31
 */
@ConfigurationProperties(prefix = "so.sao.dynamic.datasource.*")
public class DataSourceProperties {
    /**
     * jdbcUrl
     */
    private String jdbcUrl;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 驱动
     */
    private String driverClassName;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}

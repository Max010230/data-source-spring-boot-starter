package me.wuxingxing.multi.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xingxing.wu
 * @date 2018/7/17
 */
public class DataSourceContextHolder {

    public static final Logger LOGGER = LoggerFactory.getLogger(DataSourceContextHolder.class);

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DATA_SOURCE = "data-source";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源名
     *
     * @param dbType
     */
    public static void setDB(String dbType) {
        LOGGER.debug("切换到{}数据源", dbType);
        contextHolder.set(dbType);
    }

    /**
     * 获取数据源名
     *
     * @return
     */
    public static String getDB() {
        return (contextHolder.get());
    }

    /**
     * 清除数据源名
     */
    public static void clearDB() {
        contextHolder.remove();
    }
}

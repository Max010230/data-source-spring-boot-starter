package me.wuxingxing.multi.config;

import com.zaxxer.hikari.HikariDataSource;
import me.wuxingxing.multi.annotation.DynamicDataSourceAspect;
import me.wuxingxing.multi.annotation.MultiDynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xingxing.wu
 * @date 2018/7/17
 */
@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceConfig {

    @Autowired
    private DynamicDataSourceProperties properties;


    @Bean(name = "dynamicDataSource")
    public DataSource DataSourceBuild() {
        MultiDynamicDataSource multiDynamicDataSource = new MultiDynamicDataSource();
        DataSourceProperties defaultDatasource = properties.getDatasource().getOrDefault("default", null);
        if (defaultDatasource != null) {
            multiDynamicDataSource.setDefaultTargetDataSource(buildDataSource(defaultDatasource));
        }
        if (properties.getDatasource().size() != 0) {
            Map<Object, Object> map = new HashMap();
            if (defaultDatasource == null) {
                multiDynamicDataSource.setDefaultTargetDataSource(buildDataSource(properties.getDatasource().entrySet().iterator().next().getValue()));
            }
            for (String name : properties.getDatasource().keySet()) {
                map.put(name, buildDataSource(properties.getDatasource().get(name)));
            }
            multiDynamicDataSource.setTargetDataSources(map);
        }
        return multiDynamicDataSource;
    }

    @Bean(name = "dynamicSqlSessionFactory")
    public SqlSessionFactory testSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(DataSourceBuild());
        if (!StringUtils.isEmpty(properties.getConfigLocation())) {
            bean.setConfigLocation(new DefaultResourceLoader().getResource(properties.getConfigLocation()));
        }
        if (!StringUtils.isEmpty(properties.getMapperLocations())) {
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources
                    (properties.getMapperLocations()));
        }

        return bean.getObject();
    }

    public DataSource buildDataSource(DataSourceProperties dataSourceProperties) {
        return new HikariDataSource(dataSourceProperties);
    }

    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 绑定参数，以下三个方法都是参考DataSourceBuilder的bind方法实现的，
     * 目的是尽量保证我们自己添加的数据源构造过程与springboot保持一致
     *
     * @param result
     * @param properties
     */
    private void bind(DataSource result, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(new ConfigurationPropertyNameAliases())});
        //将参数绑定到对象
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    }

    private <T extends DataSource> T bind(Class<T> clazz, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(new ConfigurationPropertyNameAliases())});
        //通过类型绑定参数并获得实例对象
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get();
    }

    @Bean
    public DynamicDataSourceAspect getDynamicDataSourceAspect(){
        return new DynamicDataSourceAspect();
    }
}

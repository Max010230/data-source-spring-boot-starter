# 动态数据源切换
### 使用方法
- 添加依赖

```
compile group: "me.wuxingxing", name: "data-source-spring-boot-starter", version: "0.0.1-SNAPSHOT"
```
- 数据源配置

```
#默认数据源，不配置default会随机选择默认数据源
me.wuxingxing.dynamic.datasource.default.jdbcUrl=jdbc:mysql://xxx.xxx.xxx.xxx/xxx
me.wuxingxing.dynamic.datasource.default.username=xxx
me.wuxingxing.dynamic.datasource.default.password=xxx
me.wuxingxing.dynamic.datasource.default.driverClassName=com.mysql.jdbc.Driver

me.wuxingxing.dynamic.datasource.slave1.jdbcUrl=jdbc:mysql://xxx.xxx.xxx.xxx/xxx
me.wuxingxing.dynamic.datasource.slave1.username=xxx
me.wuxingxing.dynamic.datasource.slave1.password=xxx
me.wuxingxing.dynamic.datasource.slave1.driverClassName=com.mysql.jdbc.Driver
me.wuxingxing.dynamic.datasource.slave2.jdbcUrl=jdbc:mysql://xxx.xxx.xxx.xxx/xxx
me.wuxingxing.dynamic.datasource.slave2.username=xxx
me.wuxingxing.dynamic.datasource.slave2.password=xxx
me.wuxingxing.dynamic.datasource.slave2.driverClassName=com.mysql.jdbc.Driver

```
- 其他配置

```
so.sao.dynamic.mapper-locations=classpath:mapping/**/*.xml
so.sao.dynamic.config-location=classpath:mybatis-config.xml
```

- 注解使用

```java
@DynamicDataSource(value = "xxx")
public user findById(Long id){
    return userMapper.findById(id);
}
```
不使用注解的时候使用默认数据源
- 注意事项

```java
// 使用的时候需要禁止调自动数据源配置，配置@MapperScan(不配置会找不到mapper)
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@MapperScan(value = "xx.xxx.mapper")
```

使用PageHelper分页插件，增加配置文件

```java
@Configuration
@AutoConfigureAfter(DynamicDataSourceConfig.class)
public class PageHelperConfiguration extends PageHelperAutoConfiguration {
}
```

### 适用范围
spring boot + mybatis + 任意数据源组合
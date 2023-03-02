package com.example.nasspring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    @Value("${spring.datasource.driver-class-name}")
    String driver;

    @Value("${spring.datasource.url}")
    String jdbcUrl;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(driver)
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }

    //리플리케이션 서버의 master/slave 를 읽기/쓰기 구분하기 위한부분
    //이 부분에 slave 접속 정보를 넣으시면 됩니다.
    @Bean
    public DataSource readonlyDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(driver)
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .build();
    }


    /**
     *
     * 기본적으로 write(dataSource) 접속을 바라본다
     * 소스의 Service 단에 > @Transactional(readOnly = true) 를 넣으면 read 로 바라본다
     * 이거는 master/slave를 별도로 읽기 위한 셋팅이다.
     * by.jangjaeyong
     */
    @Bean
    public DataSource routingDataSource(@Qualifier("dataSource") DataSource dataSource, @Qualifier("readonlyDataSource") DataSource readonlyDataSource) {
        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", dataSource);
        dataSourceMap.put("read", readonlyDataSource);
        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(dataSource);

        return routingDataSource;
    }

    @Primary
    @Bean(name = "routedDataSource")
    public DataSource routedDataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource(dataSource(), readonlyDataSource()));
    }

    @Bean(name = "jdbc")
    @Autowired
    public NamedParameterJdbcTemplate readOnlyJdbcTemplate(@Qualifier("routedDataSource") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }
}
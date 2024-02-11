package com.mfw.myfilewatcher.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import(DataSourceConfigProperties.class)
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(DataSourceConfigProperties config) {

        String url = config.getDbUrl();

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder
                .create()
                .username(config.getUsername())
                .password(config.getPassword())
                .url(url);

        return dataSourceBuilder.build();
    }
}

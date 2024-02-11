package com.mfw.myfilewatcher.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "db")
public class DataSourceConfigProperties {
    private String username;
    private String password;
    private String dbName;
    private String dbUrl;
}


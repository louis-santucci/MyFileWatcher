package com.mfw.myfilewatcher.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@ConfigurationProperties(prefix = "file-watcher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileWatcherProperties {

    @NotBlank
    private String input;
    @NotBlank
    private String output;
    private boolean daemon;
    @Positive
    private Long pollInterval;
    @Positive
    private Long quietPeriod;

}

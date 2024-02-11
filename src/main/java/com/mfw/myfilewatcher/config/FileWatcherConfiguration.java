package com.mfw.myfilewatcher.config;

import com.mfw.myfilewatcher.FileListener;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ssl.SslAutoConfiguration;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableAutoConfiguration(exclude = SslAutoConfiguration.class)
@Import({
        FileWatcherProperties.class,
        DataSourceConfigProperties.class
})
@Slf4j
public class FileWatcherConfiguration {

    private FileSystemWatcher fileWatcher;

    @Bean
    FileSystemWatcher fileWatcher(
            FileWatcherProperties fileWatcherProperties,
            FileListener fileListener) {
        Duration pollIntervalDuration = Duration.of(fileWatcherProperties.getPollInterval(), ChronoUnit.SECONDS);
        Duration quietPeriodDuration = Duration.of(fileWatcherProperties.getQuietPeriod(), ChronoUnit.SECONDS);
        FileSystemWatcher fileSystemWatcher = new FileSystemWatcher(fileWatcherProperties.isDaemon(), pollIntervalDuration, quietPeriodDuration);
        File directoryFile = Path.of(fileWatcherProperties.getInput()).toFile();
        fileSystemWatcher.addSourceDirectory(directoryFile);
        // Add listener
        fileSystemWatcher.addListener(fileListener);

        fileSystemWatcher.start();
        log.info("FileWatcher initialized. Monitoring directory {}", fileWatcherProperties.getInput());

        this.fileWatcher = fileSystemWatcher;
        return fileSystemWatcher;
    }


    @PreDestroy
    public void onDestroy() {
        log.info("Shutting down FileWatcher");
        fileWatcher.stop();
    }
}

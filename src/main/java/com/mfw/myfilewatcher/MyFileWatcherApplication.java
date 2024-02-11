package com.mfw.myfilewatcher;

import com.mfw.myfilewatcher.config.FileWatcherConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FileWatcherConfiguration.class)
public class MyFileWatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyFileWatcherApplication.class, args);
    }

}

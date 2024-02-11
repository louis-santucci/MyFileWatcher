package com.mfw.myfilewatcher;

import lombok.NonNull;
import org.springframework.boot.devtools.filewatch.ChangedFile;

import java.nio.file.Path;

@FunctionalInterface
public interface FileProcessor {
    void process(@NonNull Path path, ChangedFile.Type changeType);
}

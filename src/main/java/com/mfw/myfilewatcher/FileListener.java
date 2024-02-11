package com.mfw.myfilewatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
@Slf4j
public class FileListener implements FileChangeListener {

    private boolean enabled;
    private FileProcessor fileProcessor;

    public FileListener(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
        this.enabled = true;
    }

    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        if (!this.enabled) return;
        for (ChangedFiles changedFiles : changeSet) {
            changedFiles
                    .getFiles()
                    .parallelStream()
                    .forEach(changedFile -> {
                        log.info(changedFile.toString());
                        fileProcessor.process(changedFile.getFile().toPath(), changedFile.getType());
                    });
        }
    }
}

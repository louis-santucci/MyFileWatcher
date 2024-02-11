package com.mfw.myfilewatcher;

import com.mfw.myfilewatcher.config.FileWatcherProperties;
import com.mfw.myfilewatcher.db.CopyService;
import com.mfw.myfilewatcher.db.CopyStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardCopyOption.*;

@Component
@Slf4j
@Getter
public class FileCopierProcessor implements FileProcessor {

    private final Path outputFolder;
    private final Path inputFolder;
    private final CopyService copyService;

    public FileCopierProcessor(
            FileWatcherProperties fileWatcherProperties,
            CopyService copyService) {
        this.outputFolder = Path.of(fileWatcherProperties.getOutput());
        this.inputFolder = Path.of(fileWatcherProperties.getInput());
        this.copyService = copyService;
    }

    @Override
    public void process(@NonNull Path path, @NonNull ChangedFile.Type changedType) {
        Path outputPath = null;
        CopyStatus finalStatus = CopyStatus.SUCCESS;
        try {
            if (changedType == ChangedFile.Type.ADD
                    || changedType == ChangedFile.Type.MODIFY) {
                outputPath = copy(path);
            } else if (changedType == ChangedFile.Type.DELETE) {
                delete(path);
            }
        } catch (IOException e) {
            finalStatus = CopyStatus.FAILURE;
        } finally {
            copyService.saveNewCopy(path, outputPath, changedType, finalStatus);
        }
    }

    private Path copy(Path path) throws IOException {
        Path originalPath = inputFolder.relativize(path);
        Path parentPath = originalPath.getParent();
        createFolders(parentPath);
        Path newPath = outputFolder.resolve(originalPath);
        return Files.copy(path, newPath, REPLACE_EXISTING, COPY_ATTRIBUTES);
    }

    private void delete(Path path) throws IOException {
        Path originalPath = inputFolder.relativize(path);
        Path file = outputFolder.resolve(originalPath);
        Path parentFile = file.getParent();
        if (Files.exists(file)) Files.delete(file);
        cleanFolders(parentFile);
    }

    private void createFolders(Path path) throws IOException {
        if (path == null) return;
        if (path.getParent() != null) {
            createFolders(path.getParent());
        }
        FileUtils.forceMkdir(outputFolder.resolve(path).toFile());
    }

    private void cleanFolders(Path path) throws IOException {
        if (path.equals(outputFolder)) return;
        if (!Files.exists(path)) {
            log.warn("{} doesn't exist", path);
            return;
        }
        if (Objects.requireNonNull(path.toFile().list()).length == 0) {
            FileUtils.deleteDirectory(path.toFile());
        }
        cleanFolders(path.getParent());
    }
}

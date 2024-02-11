package com.mfw.myfilewatcher;

import com.mfw.myfilewatcher.config.FileWatcherProperties;
import com.mfw.myfilewatcher.db.CopyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.devtools.filewatch.ChangedFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class FileCopierProcessorTests {

    private final FileCopierProcessor fileCopierProcessor;
    private final CopyService copyService;
    private static final String file1 = "test1.tst";
    private static final String rootTestFolder = "./target/test";
    private static final String input = rootTestFolder + "/input";
    private static final String output = rootTestFolder + "/output";

    public FileCopierProcessorTests() {
        this.copyService = Mockito.mock(CopyService.class);
        FileWatcherProperties fileWatcherProperties = new FileWatcherProperties(input, output, true, 10L, 100L);
        this.fileCopierProcessor = new FileCopierProcessor(fileWatcherProperties, copyService);
    }

    public void init() {
        ensureIOFoldersCreated();
    }

    @AfterEach
    public void cleanOutput() {
        File rootFile = new File(rootTestFolder);
        rootFile.delete();
    }

    private void ensureIOFoldersCreated() {
        File inputFile = new File(input);
        File outputFile = new File(output);
        assertThat(inputFile.exists() || inputFile.mkdirs()).isTrue();
        assertThat(outputFile.exists() || outputFile.mkdirs()).isTrue();
    }

    @Test
    void test_1() throws IOException {
        init();
        Path input = fileCopierProcessor.getInputFolder();
        Path newPath = input.resolve(file1);
        File file = newPath.toFile();
        writeToFile(file);
        fileCopierProcessor.process(newPath, ChangedFile.Type.ADD);
        Path output = fileCopierProcessor.getOutputFolder();
        Path newCopiedPath = output.resolve(file1);
        assert newCopiedPath.toFile().exists();
    }

    @Test
    void test_2_delete() throws IOException {
        init();
        Path input = fileCopierProcessor.getInputFolder();
        Path newPath = input.resolve(file1);
        File file = newPath.toFile();
        writeToFile(file);

    }

    public static void writeToFile(File file) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("This is a test");
            fw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}

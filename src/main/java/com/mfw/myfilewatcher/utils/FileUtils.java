package com.mfw.myfilewatcher.utils;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class FileUtils {
    public static Path getCurrentPath(Path path, Path origin) {
       return origin.relativize(path);
    }
}

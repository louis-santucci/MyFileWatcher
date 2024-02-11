package com.mfw.myfilewatcher.db;

import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class CopyService {
    private CopyRepository copyRepository;

    public CopyService(CopyRepository repository) {
        this.copyRepository = repository;
    }

    public List<CopyEntity> getAllCopies() {
        Iterable<CopyEntity> copiesIterable = copyRepository.findAllByOrderByCreatedAtDesc();
        List<CopyEntity> result = new ArrayList<>();
        copiesIterable.forEach(result::add);

        return result;
    }

    public CopyEntity saveNewCopy(Path inputPath, Path outputPath, ChangedFile.Type type, CopyStatus status) {

        CopyEntity copy = CopyEntity
                .builder()
                .type(type)
                .status(status)
                .originPath(inputPath.toAbsolutePath().toString())
                .destinationPath(outputPath != null ? outputPath.toAbsolutePath().toString() : null)
                .build();

        return this.copyRepository.save(copy);
    }
}

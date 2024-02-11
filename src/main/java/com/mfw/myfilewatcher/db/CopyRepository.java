package com.mfw.myfilewatcher.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CopyRepository extends CrudRepository<CopyEntity, Long> {
    Iterable<CopyEntity> findAllByOrderByCreatedAtDesc();
}

package com.mfw.myfilewatcher.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.devtools.filewatch.ChangedFile;

import java.util.Date;

@Table(name = "copy")
@Entity(name = "copy")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CopyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "origin")
    private String originPath;

    @Column(name = "destination")
    private String destinationPath;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CopyStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ChangedFile.Type type;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
}

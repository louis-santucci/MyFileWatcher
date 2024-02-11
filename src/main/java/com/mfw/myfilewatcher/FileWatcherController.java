package com.mfw.myfilewatcher;

import com.mfw.myfilewatcher.db.CopyEntity;
import com.mfw.myfilewatcher.db.CopyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Controller
public class FileWatcherController {

    private final FileSystemWatcher watcher;
    private final CopyService copyService;
    private Boolean enabled = true;

    public FileWatcherController(
            FileSystemWatcher watcher,
            CopyService copyService) {
        this.watcher = watcher;
        this.copyService = copyService;
    }

    @GetMapping("/is_enabled")
    @Operation(summary = "Returns the watcher status (enabled/disabled)")
    public ResponseEntity<Boolean> isEnabled() {
        return new ResponseEntity<>(this.enabled, OK);
    }

    @PutMapping("/toggle_watcher")
    @Operation(summary = "Enables/disables the watcher")
    public ResponseEntity<Void> toggleWatcher() {
        this.enabled = !enabled;
        if (Boolean.TRUE.equals(enabled)) {
            watcher.start();
        } else {
            watcher.stop();
        }
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/history")
    @Operation(summary = "Gets history of copies")
    public ResponseEntity<List<CopyEntity>> getHistory() {
        return new ResponseEntity<>(copyService.getAllCopies(), OK);
    }
}

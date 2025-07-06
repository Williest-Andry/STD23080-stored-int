package com.willest.storedint.endpoint.rest.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoredIntController {
  private final Path filePath;
  private final Random random = new Random();

  public StoredIntController(@Value("${stored.int.filepath:/tmp/stored-int.txt}") String path) {
    this.filePath = Path.of(path);
  }

  @GetMapping("/stored-int")
  public ResponseEntity<Integer> getStoredInt() {
    try {
      if (Files.exists(filePath)) {
        List<String> lines = Files.readAllLines(filePath);
        if (!lines.isEmpty()) {
          int value = Integer.parseInt(lines.get(0).trim());
          return ResponseEntity.ok(value);
        }
      }
      int newValue = random.nextInt(1000);
      Files.writeString(
          filePath,
          String.valueOf(newValue),
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
      return ResponseEntity.ok(newValue);
    } catch (Exception e) {
      return ResponseEntity.status(500).build();
    }
  }
}

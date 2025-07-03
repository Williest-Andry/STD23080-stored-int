package com.willest.storedint.file.hash;

import com.willest.storedint.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}

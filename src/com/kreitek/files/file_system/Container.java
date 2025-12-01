package com.kreitek.files.file_system;

import java.util.List;

public interface Container {
    List<FileSystemItem> listFiles();
    void addFile(FileSystemItem file);
    void removeFile(FileSystemItem file);
}

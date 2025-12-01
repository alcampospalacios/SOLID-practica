package com.kreitek.files.file_system;

public interface FileSystemItem {
    String getName();
    void setName(String name);
    FileSystemItem getParent();
    void setParent(FileSystemItem parent);
    String getFullPath();
    String getExtension();
    int getSize();
}

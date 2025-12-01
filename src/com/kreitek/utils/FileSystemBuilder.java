package com.kreitek.utils;

import com.kreitek.files.Directory;
import com.kreitek.files.File;
import com.kreitek.files.file_system.Container;
import com.kreitek.files.file_system.FileSystemItem;

public class FileSystemBuilder {

    private final Directory root;
    private Container currentDirectory;

    public static FileSystemBuilder getBuilder() {
        return new FileSystemBuilder();
    }

    public FileSystemBuilder() {
        root = new Directory(null, "");
        currentDirectory = root;
    }

    public FileSystemBuilder addFile(String name, int size) {
        File file = new File((FileSystemItem) currentDirectory, name);
        file.open();
        file.write(new byte[size]);
        file.close();
        currentDirectory.addFile(file);
        return this;
    }

    public FileSystemBuilder addDirectory(String name) {
        Directory directory = new Directory((FileSystemItem) currentDirectory, name);
        currentDirectory.addFile(directory);
        currentDirectory = directory;
        return this;
    }

    public FileSystemBuilder upOneDirectory() {
        FileSystemItem parent = ((FileSystemItem) currentDirectory).getParent();
        if (parent != null && parent instanceof Container) {
            currentDirectory = (Container) parent;
        }
        return this;
    }

    public FileSystemItem build() {
        return root;
    }
}

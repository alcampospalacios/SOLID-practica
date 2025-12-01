package com.kreitek.service;

import com.kreitek.files.file_system.FileSystemItem;
import java.util.List;

public class FileManager {

    public static int calculateSize(FileSystemItem fileSystemItem) {
        return fileSystemItem.getSize();
    }

    public static int calculateSize(List<FileSystemItem> files) {
        return files.stream().mapToInt(FileSystemItem::getSize).sum();
    }

    // Aquí habría otros métodos para gestionar ficheros y directorios:
    // Crear ficheros, mover ficheros, eliminar ficheros, etc.
}

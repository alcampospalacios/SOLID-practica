package com.kreitek.utils;

import com.kreitek.files.file_system.Container;
import com.kreitek.files.file_system.FileSystemItem;
import com.kreitek.service.FileManager;

public class FileSystemPrinter {

    private final FileManager fileManager;

    public FileSystemPrinter() {
        this.fileManager = new FileManager();
    }

    public static void print(FileSystemItem item, int nivel) {
        String indentation = "\t".repeat(nivel);
        String message = String.format("%s%s = %d bytes", indentation, item.getFullPath(), FileManager.calculateSize(item));
        System.out.println(message);

        if (item instanceof Container) {
            for (FileSystemItem subitem: ((Container) item).listFiles()) {
                FileSystemPrinter.print(subitem, nivel + 1);
            }
        }

    }

}

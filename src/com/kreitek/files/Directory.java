package com.kreitek.files;

import com.kreitek.files.file_system.Container;
import com.kreitek.files.file_system.FileSystemItem;
import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemItemBase implements Container {

    private final List<FileSystemItem> files;

    public Directory(FileSystemItem parent, String name) {
        super(parent, name);
        files = new ArrayList<>();
        // Aquí vendría lógica que rellena la lista de ficheros
    }

    @Override
    public String getExtension() {
        return "";
    }

    @Override
    public List<FileSystemItem> listFiles() {
        return files;
    }

    @Override
    public void addFile(FileSystemItem file) {
        if (!files.contains(file)) {
            files.add(file);
            file.setParent(this);
        }
    }

    @Override
    public void removeFile(FileSystemItem file) {
        files.remove(file);
    }

    @Override
    public int getSize() {
       return files.stream().mapToInt(FileSystemItem::getSize).sum();
    }
}

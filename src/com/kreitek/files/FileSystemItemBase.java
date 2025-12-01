package com.kreitek.files;

import com.kreitek.files.file_system.Container;
import com.kreitek.files.file_system.FileSystemItem;

public abstract class FileSystemItemBase implements FileSystemItem {
    protected static final String PATH_SEPARATOR = "/";
    protected String name;
    protected FileSystemItem parent;

    protected FileSystemItemBase(FileSystemItem parent, String name) {
        setName(name);
        setParent(parent);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }
       this.name = name;
    }

    @Override
    public FileSystemItem getParent() {
        return parent;
    }

    @Override
    public void setParent(FileSystemItem parent) {
        if (parent != null && !(parent instanceof Container)) {
            throw new IllegalArgumentException("El padre solo puede ser un contenedor");
        }
        if (this.parent != parent) {
            if (this.parent != null && this.parent instanceof Container) {
                ((Container) this.parent).removeFile(this);
            }
            this.parent = parent;
            if (parent != null && parent instanceof Container) {
                ((Container) parent).addFile(this);
            }
        }
    }

    @Override
    public String getFullPath() {
        String path = PATH_SEPARATOR;
        if (parent != null) {
            String parentFullPath = parent.getFullPath();
            path = parent.getFullPath() + (parentFullPath.length() > 1 ? PATH_SEPARATOR : "");
        }
        path = path + getName();
        return path;
    }
}
